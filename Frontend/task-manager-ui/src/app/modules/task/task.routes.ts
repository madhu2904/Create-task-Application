import { Routes } from '@angular/router';

import { moduleAuthGuard } from '../common/guards/module-auth.guard';
import { TaskDashboardComponent } from './pages/task-dashboard/task-dashboard.component';
import { TaskEndpointComponent } from './pages/task-endpoint/task-endpoint.component';

export const taskRoutes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: 'dashboard',
    component: TaskDashboardComponent,
    canActivate: [moduleAuthGuard],
    data: { moduleKey: 'task' },
    title: 'Task Dashboard',
  },
  {
    path: ':endpointKey',
    component: TaskEndpointComponent,
    canActivate: [moduleAuthGuard],
    data: { moduleKey: 'task' },
    title: 'Task Endpoint',
  },
];
