import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../../../common/services/auth.service';
import { UserModuleService } from '../../services/user-module.service';

@Component({
  selector: 'app-user-dashboard',
  imports: [RouterLink],
  templateUrl: './user-dashboard.component.html',
})
export class UserDashboardComponent {
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);
  private readonly userModuleService = inject(UserModuleService);

  readonly module = this.userModuleService.getDefinition();

  get sessionUsername(): string {
    return this.authService.session?.username ?? '';
  }

  getEndpointRoute(endpointKey: string): string {
    return this.userModuleService.getEndpointRoute(endpointKey);
  }

  async logout(): Promise<void> {
    this.authService.clearSession();
    await this.router.navigate(['/']);
  }
}
