import { Routes } from '@angular/router';

import { moduleAuthGuard } from '../common/guards/module-auth.guard';
import { UserDashboardComponent } from './pages/user-dashboard/user-dashboard.component';
import { UserEndpointComponent } from './pages/user-endpoint/user-endpoint.component';

export const userRoutes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: 'dashboard',
    component: UserDashboardComponent,
    canActivate: [moduleAuthGuard],
    data: { moduleKey: 'user' },
    title: 'User Dashboard',
  },
  {
    path: ':endpointKey',
    component: UserEndpointComponent,
    canActivate: [moduleAuthGuard],
    data: { moduleKey: 'user' },
    title: 'User Endpoint',
  },
];
