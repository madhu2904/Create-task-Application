import { Injectable } from '@angular/core';

import { ModuleDefinition } from '../../common/models/app.models';
import { notificationEndpoints } from '../data/notification-endpoints.data';
import { getModule, modules } from '../../common/data/module-definitions.data';

@Injectable({ providedIn: 'root' })
export class NotificationModuleService 
{
  getDefinition(): ModuleDefinition {
    return getModule('notification')! ;
  }

  getEndpointByRoute(routeKey: string) {
    return notificationEndpoints.find((endpoint) => endpoint.key === routeKey);
  }

  getEndpointRoute(endpointKey: string): string {
    return endpointKey;
  }
}
