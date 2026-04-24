import { Injectable } from '@angular/core';

import { ModuleDefinition } from '../../common/models/app.models';
import { projectEndpoints } from '../data/project-endpoints.data';

@Injectable({ providedIn: 'root' })
export class ProjectModuleService {
  getDefinition(): ModuleDefinition {
    return {
      key: 'project',
      title: 'Project Module',
      personName: 'Project Team',
      subtitle: 'Projects',
      description: 'Covers project CRUD endpoints.',
      username: 'project_dev',
      passwordHint: '1234',
      expectedAuthority: 'ROLE_PROJECT_MODULE_DEV',
      validationPath: '/api/v1/projects',
      endpoints: projectEndpoints,
    };
  }

  getEndpointByRoute(routeKey: string) {
    const endpointKey = routeKey === 'create' ? 'create-project' : routeKey;
    return projectEndpoints.find((endpoint) => endpoint.key === endpointKey);
  }

  getEndpointRoute(endpointKey: string): string {
    return endpointKey === 'create-project' ? 'create' : endpointKey;
  }
}
