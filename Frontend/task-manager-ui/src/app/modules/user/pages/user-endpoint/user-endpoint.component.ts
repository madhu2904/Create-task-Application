import { ChangeDetectorRef, Component, OnDestroy, OnInit, inject } from '@angular/core';
import { ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';

import { EndpointDefinition, InputFieldDefinition, ModuleDefinition, ResponseDisplayCard, Role } from '../../../common/models/app.models';
import { ApiService } from '../../../common/services/api.service';
import { ResponseCardService } from '../../../common/services/response-card.service';
import { UserModuleService } from '../../services/user-module.service';

@Component({
  selector: 'app-user-endpoint',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './user-endpoint.component.html',
})
export class UserEndpointComponent implements OnInit, OnDestroy {
  private readonly cardsPerPage = 6;
  private readonly formBuilder = inject(UntypedFormBuilder);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly apiService = inject(ApiService);
  private readonly responseCardService = inject(ResponseCardService);
  private readonly userModuleService = inject(UserModuleService);
  private readonly changeDetector = inject(ChangeDetectorRef);
  private routeSubscription?: Subscription;

  readonly module: ModuleDefinition = this.userModuleService.getDefinition();
  readonly executionForm: UntypedFormGroup = this.formBuilder.group({});

  endpoint?: EndpointDefinition;
  roles: Role[] = [];
  existingUserRoles: Role[] = [];
  selectedRoleIds: number[] = [];
  rolesToAddIds: number[] = [];
  rolesToRemoveIds: number[] = [];
  openRoleMenu: 'create' | 'update-add' | 'update-remove' | null = null;
  currentPage = 1;
  isSubmitting = false;
  errorMessage = '';
  responseData: unknown | null = null;

  get pathFields(): InputFieldDefinition[] {
    return this.endpoint?.pathParams ?? [];
  }

  get queryFields(): InputFieldDefinition[] {
    return this.endpoint?.queryParams ?? [];
  }

  get bodyFields(): InputFieldDefinition[] {
    return this.endpoint?.bodyFields ?? [];
  }

  get isCreateUser(): boolean {
    return this.endpoint?.key === 'create-user';
  }

  get isUpdateUser(): boolean {
    return this.endpoint?.key === 'update-user';
  }

  get responseCards(): ResponseDisplayCard[] {
    return this.responseData ? this.responseCardService.buildResponseCards(this.endpoint ?? null, this.responseData) : [];
  }

  get summaryCard(): ResponseDisplayCard | null {
    return this.responseCards.find((card) => card.kind === 'summary') ?? null;
  }

  get dataCards(): ResponseDisplayCard[] {
    return this.responseCards.filter((card) => card.kind === 'data');
  }

  get paginatedDataCards(): ResponseDisplayCard[] {
    const startIndex = (this.currentPage - 1) * this.cardsPerPage;
    return this.dataCards.slice(startIndex, startIndex + this.cardsPerPage);
  }

  get totalPages(): number {
    return Math.max(1, Math.ceil(this.dataCards.length / this.cardsPerPage));
  }

  ngOnInit(): void {
    this.routeSubscription = this.route.paramMap.subscribe((params) => {
      const endpoint = this.userModuleService.getEndpointByRoute(params.get('endpointKey') ?? '');

      if (!endpoint) {
        void this.router.navigate(['/user/dashboard']);
        return;
      }

      this.endpoint = endpoint;
      this.openRoleMenu = null;
      this.currentPage = 1;
      this.selectedRoleIds = [];
      this.rolesToAddIds = [];
      this.rolesToRemoveIds = [];
      this.existingUserRoles = [];
      this.rebuildForm(endpoint);
      this.clearResponse();

      if (this.isCreateUser || this.isUpdateUser) {
        void this.loadRoles();
      }
    });
  }

  ngOnDestroy(): void {
    this.routeSubscription?.unsubscribe();
  }

  getEndpointRoute(endpointKey: string): string {
    return this.userModuleService.getEndpointRoute(endpointKey);
  }

  getRoleId(role: Role): number {
    return role.userRoleId ?? role.roleId ?? role.id ?? 0;
  }

  getRoleName(role: Role): string {
    return role.roleName ?? role.name ?? `Role ${this.getRoleId(role)}`;
  }

  getRoleMenuLabel(menu: 'create' | 'update-add' | 'update-remove'): string {
    const selectedIds = this.getSelectedIds(menu);
    const sourceRoles = menu === 'update-remove' ? this.existingUserRoles : this.roles;

    if (selectedIds.length === 0) {
      return menu === 'update-remove' ? 'Select roles to remove' : 'Select roles';
    }

    return selectedIds.map((roleId) => this.getRoleNameById(roleId, sourceRoles)).join(', ');
  }

  isRoleSelected(menu: 'create' | 'update-add' | 'update-remove', roleId: number): boolean {
    return this.getSelectedIds(menu).includes(roleId);
  }

  toggleRoleMenu(menu: 'create' | 'update-add' | 'update-remove'): void {
    this.openRoleMenu = this.openRoleMenu === menu ? null : menu;
  }

  toggleRoleSelection(menu: 'create' | 'update-add' | 'update-remove', roleId: number): void {
    const nextSelectedIds = this.toggleSelection(this.getSelectedIds(menu), roleId);

    if (menu === 'create') {
      this.selectedRoleIds = nextSelectedIds;
      return;
    }

    if (menu === 'update-add') {
      this.rolesToAddIds = nextSelectedIds.filter((selectedId) => !this.rolesToRemoveIds.includes(selectedId));
      return;
    }

    this.rolesToRemoveIds = nextSelectedIds.filter((selectedId) => !this.rolesToAddIds.includes(selectedId));
  }

  removeSelectedRole(menu: 'create' | 'update-add' | 'update-remove', roleId: number): void {
    this.toggleRoleSelection(menu, roleId);
  }

  async loadUserRolesForEdit(): Promise<void> {
    const userId = this.getUserIdFromForm();

    if (!userId) {
      this.errorMessage = 'Enter a valid User Id to load current roles.';
      return;
    }

    this.errorMessage = '';

    try {
      this.existingUserRoles = await this.apiService.getUserRoles(userId);
      this.rolesToRemoveIds = this.rolesToRemoveIds.filter((roleId) => this.existingUserRoles.some((role) => this.getRoleId(role) === roleId));
    } catch (error) {
      this.errorMessage = this.apiService.toErrorMessage(error);
    } finally {
      this.changeDetector.detectChanges();
    }
  }

  goToPreviousPage(): void {
    this.currentPage = Math.max(1, this.currentPage - 1);
  }

  goToNextPage(): void {
    this.currentPage = Math.min(this.totalPages, this.currentPage + 1);
  }

  async runEndpoint(): Promise<void> {
    if (!this.endpoint || this.executionForm.invalid) {
      return;
    }

    this.errorMessage = '';
    this.isSubmitting = true;

    try {
      const values = this.executionForm.getRawValue();
      this.responseData = this.isCreateUser
        ? await this.createUserWithRoles(values)
        : this.isUpdateUser
          ? await this.updateUserWithRoles(values)
          : await this.apiService.executeEndpoint(this.endpoint, values);
      this.currentPage = 1;
    } catch (error) {
      this.errorMessage = this.apiService.toErrorMessage(error);
    } finally {
      this.isSubmitting = false;
      this.changeDetector.detectChanges();
    }
  }

  clearResponse(): void {
    this.responseData = null;
    this.errorMessage = '';
    this.currentPage = 1;
  }

  private async loadRoles(): Promise<void> {
    try {
      const roles = await this.apiService.getRoles();
      const seenRoleKeys = new Set<string>();

      this.roles = roles.filter((role) => {
        const roleKey = `${this.getRoleId(role)}::${this.getRoleName(role).trim().toLowerCase()}`;
        if (seenRoleKeys.has(roleKey)) {
          return false;
        }

        seenRoleKeys.add(roleKey);
        return true;
      });
    } catch {
      this.roles = [];
    } finally {
      this.changeDetector.detectChanges();
    }
  }

  private async createUserWithRoles(values: Record<string, string>): Promise<unknown> {
    const response = await this.apiService.createUser(values);
    const userId = this.findId(response, ['userId', 'id']);

    if (!userId) {
      return response;
    }

    for (const roleId of this.selectedRoleIds) {
      await this.apiService.assignRoleToUser(userId, roleId);
    }

    return this.apiService.getUserById(userId);
  }

  private async updateUserWithRoles(values: Record<string, string>): Promise<unknown> {
    if (!this.endpoint) {
      return null;
    }

    const response = await this.apiService.executeEndpoint(this.endpoint, values);
    const userId = this.getUserIdFromForm() ?? this.findId(response, ['userId', 'id']);

    if (!userId) {
      return response;
    }

    for (const roleId of this.rolesToAddIds) {
      await this.apiService.assignRoleToUser(userId, roleId);
    }

    for (const roleId of this.rolesToRemoveIds) {
      await this.apiService.removeRoleFromUser(userId, roleId);
    }

    this.existingUserRoles = await this.apiService.getUserRoles(userId);
    this.rolesToAddIds = [];
    this.rolesToRemoveIds = [];
    return this.apiService.getUserById(userId);
  }

  private findId(response: unknown, keys: string[]): number | null {
    if (!this.isObject(response)) {
      return null;
    }

    const data = this.isObject(response['data']) ? response['data'] : response;

    for (const key of keys) {
      const value = data[key];
      if (typeof value === 'number') {
        return value;
      }
    }

    return null;
  }

  private isObject(value: unknown): value is Record<string, unknown> {
    return typeof value === 'object' && value !== null && !Array.isArray(value);
  }

  private getSelectedIds(menu: 'create' | 'update-add' | 'update-remove'): number[] {
    if (menu === 'create') {
      return this.selectedRoleIds;
    }

    return menu === 'update-add' ? this.rolesToAddIds : this.rolesToRemoveIds;
  }

  private toggleSelection(values: number[], id: number): number[] {
    return values.includes(id) ? values.filter((value) => value !== id) : [...values, id];
  }

  getRoleNameById(roleId: number, sourceRoles: Role[]): string {
    const role = sourceRoles.find((item) => this.getRoleId(item) === roleId);
    return role ? this.getRoleName(role) : `Role ${roleId}`;
  }

  private getUserIdFromForm(): number | null {
    const value = this.executionForm.get('userId')?.value;
    const userId = Number(value);
    return Number.isFinite(userId) && userId > 0 ? userId : null;
  }

  private rebuildForm(endpoint: EndpointDefinition): void {
    for (const key of Object.keys(this.executionForm.controls)) {
      this.executionForm.removeControl(key);
    }

    for (const field of this.allFields(endpoint)) {
      this.executionForm.addControl(field.key, this.formBuilder.control('', field.required ? Validators.required : []));
    }
  }

  private allFields(endpoint: EndpointDefinition): InputFieldDefinition[] {
    return [...(endpoint.pathParams ?? []), ...(endpoint.queryParams ?? []), ...(endpoint.bodyFields ?? [])];
  }
}
