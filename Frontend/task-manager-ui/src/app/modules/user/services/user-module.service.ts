import { Injectable } from '@angular/core';

import { ModuleDefinition } from '../../common/models/app.models';
import { userEndpoints } from '../data/user-endpoints.data';
import { getModule } from '../../common/data/module-definitions.data';

@Injectable({ providedIn: 'root' })
export class UserModuleService {
  getDefinition(): ModuleDefinition {
    return getModule('user')! ;
  }

  getEndpointByRoute(routeKey: string) {
    const endpointKey = routeKey === 'create' ? 'create-user' : routeKey;
    return userEndpoints.find((endpoint) => endpoint.key === endpointKey);
  }

  getEndpointRoute(endpointKey: string): string {
    return endpointKey === 'create-user' ? 'create' : endpointKey;
  }
}
