import { Routes } from '@angular/router';

import { moduleAuthGuard } from '../common/guards/module-auth.guard';
import { CollaborationDashboardComponent } from './pages/collaboration-dashboard/collaboration-dashboard.component';
import { CollaborationEndpointComponent } from './pages/collaboration-endpoint/collaboration-endpoint.component';

export const collaborationRoutes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: 'dashboard',
    component: CollaborationDashboardComponent,
    canActivate: [moduleAuthGuard],
    data: { moduleKey: 'collaboration' },
    title: 'Collaboration Dashboard',
  },
  {
    path: ':endpointKey',
    component: CollaborationEndpointComponent,
    canActivate: [moduleAuthGuard],
    data: { moduleKey: 'collaboration' },
    title: 'Collaboration Endpoint',
  },
];
