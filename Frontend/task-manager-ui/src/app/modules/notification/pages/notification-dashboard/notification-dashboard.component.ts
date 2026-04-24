import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../../../common/services/auth.service';
import { NotificationModuleService } from '../../services/notification-module.service';

@Component({
  selector: 'app-notification-dashboard',
  imports: [RouterLink],
  templateUrl: './notification-dashboard.component.html',
})
export class NotificationDashboardComponent {
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);
  private readonly notificationModuleService = inject(NotificationModuleService);

  readonly module = this.notificationModuleService.getDefinition();

  get sessionUsername(): string {
    return this.authService.session?.username ?? '';
  }

  getEndpointRoute(endpointKey: string): string {
    return this.notificationModuleService.getEndpointRoute(endpointKey);
  }

  async logout(): Promise<void> {
    this.authService.clearSession();
    await this.router.navigate(['/']);
  }
}
