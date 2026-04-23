import { Routes } from '@angular/router';
import { NotificationList } from './components/notification-list/notification-list';
import { NotificationDetail} from './components/notification-detail/notification-detail';
import { NotificationCreate } from './components/notification-create/notification-create';
import { NotificationUser} from './components/notification-user/notification-user';
import { NotificationUnread } from './components/notification-unread/notification-unread';
import { NotificationRead} from './components/notification-read/notification-read';

export const NOTIFICATION_ROUTES: Routes = [

  { path: '', redirectTo: 'list', pathMatch: 'full' },

  { path: 'list', component: NotificationList },
  { path: 'detail/:id', component: NotificationDetail },
  { path: 'create', component: NotificationCreate },
  { path: 'user/:userId', component: NotificationUser },
  { path: 'unread/:userId', component: NotificationUnread},
  { path: 'read/:id', component: NotificationRead }
];