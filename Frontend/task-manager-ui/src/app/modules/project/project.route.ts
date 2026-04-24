import { Routes } from '@angular/router';

import { moduleAuthGuard } from '../common/guards/module-auth.guard';
import { ProjectDashboardComponent } from './pages/project-dashboard/project-dashboard.component';
import { ProjectEndpointComponent } from './pages/project-endpoint/project-endpoint.component';

export const projectRoutes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: 'dashboard',
    component: ProjectDashboardComponent,
    canActivate: [moduleAuthGuard],
    data: { moduleKey: 'project' },
    title: 'Project Dashboard',
  },
  {
    path: ':endpointKey',
    component: ProjectEndpointComponent,
    canActivate: [moduleAuthGuard],
    data: { moduleKey: 'project' },
    title: 'Project Endpoint',
  },
];
