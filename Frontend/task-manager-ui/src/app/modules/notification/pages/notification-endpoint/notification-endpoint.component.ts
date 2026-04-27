import { ChangeDetectorRef, Component, OnDestroy, OnInit, inject } from '@angular/core';
import { ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';

import { EndpointDefinition, InputFieldDefinition, ModuleDefinition, ResponseDisplayCard } from '../../../common/models/app.models';
import { ApiService } from '../../../common/services/api.service';
import { ResponseCardService } from '../../../common/services/response-card.service';
import { NotificationModuleService } from '../../services/notification-module.service';

@Component({
  selector: 'app-notification-endpoint',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './notification-endpoint.component.html',
})
export class NotificationEndpointComponent implements OnInit, OnDestroy {
  private readonly cardsPerPage = 6;
  private readonly formBuilder = inject(UntypedFormBuilder);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly apiService = inject(ApiService);
  private readonly responseCardService = inject(ResponseCardService);
  private readonly notificationModuleService = inject(NotificationModuleService);
  private readonly changeDetector = inject(ChangeDetectorRef);
  private routeSubscription?: Subscription;


  readonly module: ModuleDefinition = this.notificationModuleService.getDefinition();

  // untyped form is used since we don't know the datatypes now..it is dynamic based on endpoint selection
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
      const endpoint = this.notificationModuleService.getEndpointByRoute(params.get('endpointKey') ?? '');

      if (!endpoint) {
        void this.router.navigate(['/notification/dashboard']);
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
//for example if create-user is passed ..create will be given as response according to our code in service
  getEndpointRoute(endpointKey: string): string {
    return this.notificationModuleService.getEndpointRoute(endpointKey);
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
// used to rebuild the form when the control changes . i.e when a different end pint is clicked all current form fields cleared new one's populated
  private rebuildForm(endpoint: EndpointDefinition): void 
  {
    for (const key of Object.keys(this.executionForm.controls)) {
      this.executionForm.removeControl(key);
    }

    for (const field of this.allFields(endpoint)) {
      this.executionForm.addControl(field.key, this.formBuilder.control('', field.required ? Validators.required : []));
    }
  }
// heler method for rebildForm
  private allFields(endpoint: EndpointDefinition): InputFieldDefinition[] {
    return [...(endpoint.pathParams ?? []), ...(endpoint.queryParams ?? []), ...(endpoint.bodyFields ?? [])];
  }
}
