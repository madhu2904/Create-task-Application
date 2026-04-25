import { Injectable } from '@angular/core';

import { ModuleDefinition } from '../../common/models/app.models';
import { projectEndpoints } from '../data/project-endpoints.data';
import { getModule } from '../../common/data/module-definitions.data';

@Injectable({ providedIn: 'root' })
export class ProjectModuleService {
  getDefinition(): ModuleDefinition {
    return getModule('project')! ;
  }

  getEndpointByRoute(routeKey: string) {
    const endpointKey = routeKey === 'create' ? 'create-project' : routeKey;
    return projectEndpoints.find((endpoint) => endpoint.key === endpointKey);
  }

  getEndpointRoute(endpointKey: string): string {
    return endpointKey === 'create-project' ? 'create' : endpointKey;
  }
}
