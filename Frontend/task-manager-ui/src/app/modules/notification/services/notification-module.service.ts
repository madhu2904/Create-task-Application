import { Injectable } from '@angular/core';

import { ModuleDefinition } from '../../common/models/app.models';
import { notificationEndpoints } from '../data/notification-endpoints.data';

@Injectable({ providedIn: 'root' })
export class NotificationModuleService {
  getDefinition(): ModuleDefinition {
    return {
      key: 'notification',
      title: 'Notification Module',
      personName: 'Notification Team',
      subtitle: 'User notifications',
      description: 'Covers notification lookup endpoints.',
      username: 'notification_dev',
      passwordHint: '1234',
      expectedAuthority: 'ROLE_NOTIFICATION_MODULE_DEV',
      validationPath: '/api/v1/notifications/1',
      endpoints: notificationEndpoints,
    };
  }

  getEndpointByRoute(routeKey: string) {
    return notificationEndpoints.find((endpoint) => endpoint.key === routeKey);
  }

  getEndpointRoute(endpointKey: string): string {
    return endpointKey;
  }
}
