import { Routes } from '@angular/router';

import { HomeComponent } from './modules/common/pages/home/home.component';
import { ModuleLoginComponent } from './modules/common/pages/login/module-login.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Task Manager Modules',
  },
  {
    path: 'modules/:moduleKey/login',
    component: ModuleLoginComponent,
    title: 'Module Login',
  },
  {
    path: 'user',
    loadChildren: () => import('./modules/user/user.routes').then((routes) => routes.userRoutes),
  },
  {
    path: 'task',
    loadChildren: () => import('./modules/task/task.routes').then((routes) => routes.taskRoutes),
  },
  {
    path: 'project',
    loadChildren: () => import('./modules/project/project.routes').then((routes) => routes.projectRoutes),
  },
  {
    path: 'notification',
    loadChildren: () => import('./modules/notification/notification.routes').then((routes) => routes.notificationRoutes),
  },
  {
    path: 'collaboration',
    loadChildren: () => import('./modules/collaboration/collaboration.routes').then((routes) => routes.collaborationRoutes),
  },
  {
    path: '**',
    redirectTo: '',
  },
];
