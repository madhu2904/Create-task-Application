import { Injectable } from '@angular/core';

import { ModuleDefinition } from '../../common/models/app.models';
import { taskEndpoints } from '../data/task-endpoints.data';
import { getModule } from '../../common/data/module-definitions.data';

@Injectable({ providedIn: 'root' })
export class TaskModuleService {
  getDefinition(): ModuleDefinition {
    return getModule('task')! ;
  }

  getEndpointByRoute(routeKey: string) {
    const endpointKey = routeKey === 'create' ? 'create-task' : routeKey;
    return taskEndpoints.find((endpoint) => endpoint.key === endpointKey);
  }

  getEndpointRoute(endpointKey: string): string {
    return endpointKey === 'create-task' ? 'create' : endpointKey;
  }
}
