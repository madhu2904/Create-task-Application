import { Routes } from '@angular/router';

import { moduleAuthGuard } from '../common/guards/module-auth.guard';
import { NotificationDashboardComponent } from './pages/notification-dashboard/notification-dashboard.component';
import { NotificationEndpointComponent } from './pages/notification-endpoint/notification-endpoint.component';

export const notificationRoutes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: 'dashboard',
    component: NotificationDashboardComponent,
    canActivate: [moduleAuthGuard],
    data: { moduleKey: 'notification' },
    title: 'Notification Dashboard',
  },
  {
    path: ':endpointKey',
    component: NotificationEndpointComponent,
    canActivate: [moduleAuthGuard],
    data: { moduleKey: 'notification' },
    title: 'Notification Endpoint',
  },
];
