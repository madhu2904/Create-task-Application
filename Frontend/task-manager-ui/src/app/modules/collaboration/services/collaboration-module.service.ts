import { Injectable } from '@angular/core';

import { ModuleDefinition } from '../../common/models/app.models';
import { collaborationEndpoints } from '../data/collaboration-endpoints.data';

@Injectable({ providedIn: 'root' })
export class CollaborationModuleService {
  getDefinition(): ModuleDefinition {
    return {
      key: 'collaboration',
      title: 'Collaboration Module',
      personName: 'Collaboration Team',
      subtitle: 'Comments and attachments',
      description: 'Covers task comments and task attachment endpoints.',
      // username: 'comment_dev',
      // passwordHint: '1234',
      expectedAuthority: 'ROLE_COMMENT_ATTACHMENT_MODULE_DEV',
      validationPath: '/api/v1/tasks/1/comments',
      endpoints: collaborationEndpoints,
    };
  }

  getEndpointByRoute(routeKey: string) {
    const endpointKey = routeKey === 'comment' ? 'create-comment' : routeKey;
    return collaborationEndpoints.find((endpoint) => endpoint.key === endpointKey);
  }

  getEndpointRoute(endpointKey: string): string {
    return endpointKey === 'create-comment' ? 'comment' : endpointKey;
  }
}
