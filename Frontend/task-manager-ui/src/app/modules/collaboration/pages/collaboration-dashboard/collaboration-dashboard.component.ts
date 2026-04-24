import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../../../common/services/auth.service';
import { CollaborationModuleService } from '../../services/collaboration-module.service';

@Component({
  selector: 'app-collaboration-dashboard',
  imports: [RouterLink],
  templateUrl: './collaboration-dashboard.component.html',
})
export class CollaborationDashboardComponent {
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);
  private readonly collaborationModuleService = inject(CollaborationModuleService);

  readonly module = this.collaborationModuleService.getDefinition();

  get sessionUsername(): string {
    return this.authService.session?.username ?? '';
  }

  getEndpointRoute(endpointKey: string): string {
    return this.collaborationModuleService.getEndpointRoute(endpointKey);
  }

  async logout(): Promise<void> {
    this.authService.clearSession();
    await this.router.navigate(['/']);
  }
}
