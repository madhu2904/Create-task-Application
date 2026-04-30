import { ChangeDetectorRef, Component, OnDestroy, OnInit, inject } from '@angular/core';
import { ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';

import { EndpointDefinition, InputFieldDefinition, ModuleDefinition, ResponseDisplayCard } from '../../../common/models/app.models';
import { ApiService } from '../../../common/services/api.service';
import { ResponseCardService } from '../../../common/services/response-card.service';
import { CollaborationModuleService } from '../../services/collaboration-module.service';

@Component({
  selector: 'app-collaboration-endpoint',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './collaboration-endpoint.component.html',
})
export class CollaborationEndpointComponent implements OnInit, OnDestroy {
  private readonly cardsPerPage = 6;
  private readonly formBuilder = inject(UntypedFormBuilder);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly apiService = inject(ApiService);
  private readonly responseCardService = inject(ResponseCardService);
  private readonly collaborationModuleService = inject(CollaborationModuleService);
  private readonly changeDetector = inject(ChangeDetectorRef);
  private routeSubscription?: Subscription;

  readonly module: ModuleDefinition = this.collaborationModuleService.getDefinition();
  readonly executionForm: UntypedFormGroup = this.formBuilder.group({});

  endpoint?: EndpointDefinition;
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
      const endpoint = this.collaborationModuleService.getEndpointByRoute(params.get('endpointKey') ?? '');

      if (!endpoint) {
        void this.router.navigate(['/collaboration/dashboard']);
        return;
      }

      this.endpoint = endpoint;
      this.currentPage = 1;
      this.rebuildForm(endpoint);
      this.clearResponse();
    });
  }

  ngOnDestroy(): void {
    this.routeSubscription?.unsubscribe();
  }

  getEndpointRoute(endpointKey: string): string {
    return this.collaborationModuleService.getEndpointRoute(endpointKey);
  }

  goToPreviousPage(): void {
    this.currentPage = Math.max(1, this.currentPage - 1);
  }

  goToNextPage(): void {
    this.currentPage = Math.min(this.totalPages, this.currentPage + 1);
  }

  async runEndpoint(): Promise<void> 
  {
    this.responseData=null
    this.currentPage=1;
    if (!this.endpoint || this.executionForm.invalid) {
      return;
    }

    this.errorMessage = '';
    this.isSubmitting = true;

    try {
      this.responseData = await this.apiService.executeEndpoint(this.endpoint, this.executionForm.getRawValue());
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
