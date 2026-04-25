import { Injectable } from '@angular/core';

import { ModuleDefinition } from '../../common/models/app.models';
import { collaborationEndpoints } from '../data/collaboration-endpoints.data';
import { getModule } from '../../common/data/module-definitions.data';

@Injectable({ providedIn: 'root' })
export class CollaborationModuleService {
  getDefinition(): ModuleDefinition {
    return getModule('collaboration')! ;
  }

  getEndpointByRoute(routeKey: string) {
    const endpointKey = routeKey === 'comment' ? 'create-comment' : routeKey;
    return collaborationEndpoints.find((endpoint) => endpoint.key === endpointKey);
  }

  getEndpointRoute(endpointKey: string): string {
    return endpointKey === 'create-comment' ? 'comment' : endpointKey;
  }
}
