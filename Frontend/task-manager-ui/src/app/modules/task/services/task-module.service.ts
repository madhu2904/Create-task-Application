import { Injectable } from '@angular/core';

import { ModuleDefinition } from '../../common/models/app.models';
import { taskEndpoints } from '../data/task-endpoints.data';

@Injectable({ providedIn: 'root' })
export class TaskModuleService {
  getDefinition(): ModuleDefinition {
    return {
      key: 'task',
      title: 'Task Module',
      personName: 'Sarmisha',
      subtitle: 'Tasks and categories',
      description: 'Covers task management, filters, and category mapping endpoints.',
      username: 'task_dev',
      passwordHint: '1234',
      expectedAuthority: 'ROLE_TASK_MODULE_DEV',
      validationPath: '/api/v1/tasks',
      endpoints: taskEndpoints,
    };
  }

  getEndpointByRoute(routeKey: string) {
    const endpointKey = routeKey === 'create' ? 'create-task' : routeKey;
    return taskEndpoints.find((endpoint) => endpoint.key === endpointKey);
  }

  getEndpointRoute(endpointKey: string): string {
    return endpointKey === 'create-task' ? 'create' : endpointKey;
  }
}
