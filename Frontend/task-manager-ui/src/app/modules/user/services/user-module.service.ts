import { Injectable } from '@angular/core';

import { ModuleDefinition } from '../../common/models/app.models';
import { userEndpoints } from '../data/user-endpoints.data';

@Injectable({ providedIn: 'root' })
export class UserModuleService {
  getDefinition(): ModuleDefinition {
    return {
      key: 'user',
      title: 'User Module',
      personName: 'Ilakiyaa',
      subtitle: 'Users and roles',
      description: 'Covers user CRUD operations and user-role mapping endpoints.',
      username: 'user_dev',
      passwordHint: '1234',
      expectedAuthority: 'ROLE_USER_MODULE_DEV',
      validationPath: '/api/v1/users',
      endpoints: userEndpoints,
    };
  }

  getEndpointByRoute(routeKey: string) {
    const endpointKey = routeKey === 'create' ? 'create-user' : routeKey;
    return userEndpoints.find((endpoint) => endpoint.key === endpointKey);
  }

  getEndpointRoute(endpointKey: string): string {
    return endpointKey === 'create-user' ? 'create' : endpointKey;
  }
}
