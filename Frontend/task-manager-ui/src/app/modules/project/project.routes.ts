import { Routes } from '@angular/router';

import { moduleAuthGuard } from '../common/guards/module-auth.guard';
import { ProjectDashboardComponent } from './pages/project-dashboard/project-dashboard.component';
import { ProjectEndpointComponent } from './pages/project-endpoint/project-endpoint.component';

export const projectRoutes: Routes = [
  //by default it will load first dashboard page
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: 'dashboard',
    component: ProjectDashboardComponent,
    canActivate: [moduleAuthGuard],   //when user goes project/dashboard auth guard checks
    data: { moduleKey: 'project' },
    title: 'Project Dashboard',
  },
  //if any endpoints are clicked endpoint component is loaded
  //dynamic routing all endpoints go to same component
  {
    path: ':endpointKey',
    component: ProjectEndpointComponent,
    canActivate: [moduleAuthGuard],
    data: { moduleKey: 'project' },
    title: 'Project Endpoint',
  },
];
