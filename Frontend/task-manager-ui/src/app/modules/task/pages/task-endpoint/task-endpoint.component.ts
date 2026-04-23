import { ChangeDetectorRef, Component, OnDestroy, OnInit, inject } from '@angular/core';
import { ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';

import { Category, EndpointDefinition, InputFieldDefinition, ModuleDefinition, ResponseDisplayCard } from '../../../common/models/app.models';
import { ApiService } from '../../../common/services/api.service';
import { ResponseCardService } from '../../../common/services/response-card.service';
import { TaskModuleService } from '../../services/task-module.service';

@Component({
  selector: 'app-task-endpoint',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './task-endpoint.component.html',
})
export class TaskEndpointComponent implements OnInit, OnDestroy {
  private readonly cardsPerPage = 6;
  private readonly formBuilder = inject(UntypedFormBuilder);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly apiService = inject(ApiService);
  private readonly responseCardService = inject(ResponseCardService);
  private readonly taskModuleService = inject(TaskModuleService);
  private readonly changeDetector = inject(ChangeDetectorRef);
  private routeSubscription?: Subscription;

  readonly module: ModuleDefinition = this.taskModuleService.getDefinition();
  readonly executionForm: UntypedFormGroup = this.formBuilder.group({});

  endpoint?: EndpointDefinition;
  categories: Category[] = [];
  existingTaskCategories: Category[] = [];
  selectedCategoryIds: number[] = [];
  categoriesToAddIds: number[] = [];
  categoriesToRemoveIds: number[] = [];
  openCategoryMenu: 'create' | 'update-add' | 'update-remove' | null = null;
  isLoadingCategories = false;
  categoryLoadError = '';
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

  get isCreateTask(): boolean {
    return this.endpoint?.key === 'create-task';
  }

  get isUpdateTask(): boolean {
    return this.endpoint?.key === 'update-task';
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
      const endpoint = this.taskModuleService.getEndpointByRoute(params.get('endpointKey') ?? '');

      if (!endpoint) {
        void this.router.navigate(['/task/dashboard']);
        return;
      }

      this.endpoint = endpoint;
      this.openCategoryMenu = null;
      this.currentPage = 1;
      this.selectedCategoryIds = [];
      this.categoriesToAddIds = [];
      this.categoriesToRemoveIds = [];
      this.existingTaskCategories = [];
      this.rebuildForm(endpoint);
      this.clearResponse();

      if (this.isCreateTask || this.isUpdateTask) {
        void this.loadCategories();
      }
    });
  }

  ngOnDestroy(): void {
    this.routeSubscription?.unsubscribe();
  }

  getEndpointRoute(endpointKey: string): string {
    return this.taskModuleService.getEndpointRoute(endpointKey);
  }

  getCategoryId(category: Category): number {
    return category.categoryId ?? category.id ?? 0;
  }

  getCategoryName(category: Category): string {
    return category.categoryName ?? category.name ?? `Category ${this.getCategoryId(category)}`;
  }

  getCategoryMenuLabel(menu: 'create' | 'update-add' | 'update-remove'): string {
    const selectedIds = this.getSelectedCategoryIds(menu);
    const sourceCategories = menu === 'update-remove' ? this.existingTaskCategories : this.categories;

    if (selectedIds.length === 0) {
      return menu === 'update-remove' ? 'Select categories to remove' : 'Select categories';
    }

    return selectedIds.map((categoryId) => this.getCategoryNameById(categoryId, sourceCategories)).join(', ');
  }

  isCategorySelected(menu: 'create' | 'update-add' | 'update-remove', categoryId: number): boolean {
    return this.getSelectedCategoryIds(menu).includes(categoryId);
  }

  toggleCategoryMenu(menu: 'create' | 'update-add' | 'update-remove'): void {
    this.openCategoryMenu = this.openCategoryMenu === menu ? null : menu;
  }

  toggleCategorySelection(menu: 'create' | 'update-add' | 'update-remove', categoryId: number): void {
    const nextSelectedIds = this.toggleSelection(this.getSelectedCategoryIds(menu), categoryId);

    if (menu === 'create') {
      this.selectedCategoryIds = nextSelectedIds;
      return;
    }

    if (menu === 'update-add') {
      this.categoriesToAddIds = nextSelectedIds.filter((selectedId) => !this.categoriesToRemoveIds.includes(selectedId));
      return;
    }

    this.categoriesToRemoveIds = nextSelectedIds.filter((selectedId) => !this.categoriesToAddIds.includes(selectedId));
  }

  removeSelectedCategory(menu: 'create' | 'update-add' | 'update-remove', categoryId: number): void {
    this.toggleCategorySelection(menu, categoryId);
  }

  async loadTaskCategoriesForEdit(): Promise<void> {
    const taskId = this.getTaskIdFromForm();

    if (!taskId) {
      this.errorMessage = 'Enter a valid Task Id to load current categories.';
      return;
    }

    this.errorMessage = '';

    try {
      this.existingTaskCategories = await this.apiService.getTaskCategories(taskId);
      this.categoriesToRemoveIds = this.categoriesToRemoveIds.filter((categoryId) =>
        this.existingTaskCategories.some((category) => this.getCategoryId(category) === categoryId),
      );
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
      this.responseData = this.isCreateTask
        ? await this.createTaskWithCategories(values)
        : this.isUpdateTask
          ? await this.updateTaskWithCategories(values)
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

  reloadCategories(): void {
    void this.loadCategories();
  }

  private async loadCategories(): Promise<void> {
    this.isLoadingCategories = true;
    this.categoryLoadError = '';

    try {
      const categories = await this.apiService.getCategories();
      const seenCategoryKeys = new Set<string>();

      this.categories = categories.filter((category) => {
        const categoryKey = `${this.getCategoryId(category)}::${this.getCategoryName(category).trim().toLowerCase()}`;
        if (seenCategoryKeys.has(categoryKey)) {
          return false;
        }

        seenCategoryKeys.add(categoryKey);
        return true;
      });
    } catch (error) {
      this.categories = [];
      this.categoryLoadError = this.apiService.toErrorMessage(error);
    } finally {
      this.isLoadingCategories = false;
      this.changeDetector.detectChanges();
    }
  }

  private async createTaskWithCategories(values: Record<string, string>): Promise<unknown> {
    const response = await this.apiService.createTask(values, this.selectedCategoryIds);
    const taskId = this.findId(response, ['taskId', 'id']);

    if (!taskId) {
      return response;
    }

    return this.buildTaskResponseWithCategories(taskId);
  }

  private async updateTaskWithCategories(values: Record<string, string>): Promise<unknown> {
    const taskId = this.getTaskIdFromForm();

    if (!taskId) {
      return null;
    }

    await this.apiService.updateTask(taskId, values, this.existingTaskCategories.map((category) => this.getCategoryId(category)));

    for (const categoryId of this.categoriesToAddIds) {
      await this.apiService.addCategoryToTask(taskId, categoryId);
    }

    for (const categoryId of this.categoriesToRemoveIds) {
      await this.apiService.removeCategoryFromTask(taskId, categoryId);
    }

    this.existingTaskCategories = await this.apiService.getTaskCategories(taskId);
    this.categoriesToAddIds = [];
    this.categoriesToRemoveIds = [];
    return this.buildTaskResponseWithCategories(taskId);
  }

  private async buildTaskResponseWithCategories(taskId: number): Promise<unknown> {
    const taskResponse = await this.apiService.getTaskById(taskId);
    const categories = await this.apiService.getTaskCategories(taskId).catch(() => []);

    if (!this.isObject(taskResponse)) {
      return taskResponse;
    }

    const data = this.isObject(taskResponse['data']) ? taskResponse['data'] : null;

    return data
      ? {
          ...taskResponse,
          data: {
            ...data,
            categories,
          },
        }
      : taskResponse;
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

  getCategoryNameById(categoryId: number, sourceCategories: Category[]): string {
    const category = sourceCategories.find((item) => this.getCategoryId(item) === categoryId);
    return category ? this.getCategoryName(category) : `Category ${categoryId}`;
  }

  private getSelectedCategoryIds(menu: 'create' | 'update-add' | 'update-remove'): number[] {
    if (menu === 'create') {
      return this.selectedCategoryIds;
    }

    return menu === 'update-add' ? this.categoriesToAddIds : this.categoriesToRemoveIds;
  }

  private toggleSelection(values: number[], id: number): number[] {
    return values.includes(id) ? values.filter((value) => value !== id) : [...values, id];
  }

  private getTaskIdFromForm(): number | null {
    const value = this.executionForm.get('taskId')?.value;
    const taskId = Number(value);
    return Number.isFinite(taskId) && taskId > 0 ? taskId : null;
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
}
