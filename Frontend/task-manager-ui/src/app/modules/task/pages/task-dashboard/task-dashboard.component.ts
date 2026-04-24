import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../../../common/services/auth.service';
import { TaskModuleService } from '../../services/task-module.service';

@Component({
  selector: 'app-task-dashboard',
  imports: [RouterLink],
  templateUrl: './task-dashboard.component.html',
})
export class TaskDashboardComponent {
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);
  private readonly taskModuleService = inject(TaskModuleService);

  readonly module = this.taskModuleService.getDefinition();

  get sessionUsername(): string {
    return this.authService.session?.username ?? '';
  }

  getEndpointRoute(endpointKey: string): string {
    return this.taskModuleService.getEndpointRoute(endpointKey);
  }

  async logout(): Promise<void> {
    this.authService.clearSession();
    await this.router.navigate(['/']);
  }
}
