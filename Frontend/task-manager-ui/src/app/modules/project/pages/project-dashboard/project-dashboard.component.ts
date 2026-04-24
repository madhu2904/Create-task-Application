import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../../../common/services/auth.service';
import { ProjectModuleService } from '../../services/project-module.service';

@Component({
  selector: 'app-project-dashboard',
  imports: [RouterLink],
  templateUrl: './project-dashboard.component.html',
})
export class ProjectDashboardComponent {
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);
  private readonly projectModuleService = inject(ProjectModuleService);

  readonly module = this.projectModuleService.getDefinition();

  get sessionUsername(): string {
    return this.authService.session?.username ?? '';
  }

  getEndpointRoute(endpointKey: string): string {
    return this.projectModuleService.getEndpointRoute(endpointKey);
  }

  async logout(): Promise<void> {
    this.authService.clearSession();
    await this.router.navigate(['/']);
  }
}
