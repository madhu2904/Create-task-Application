import { ModuleDefinition } from '../models/app.models';
import { collaborationEndpoints } from '../../collaboration/data/collaboration-endpoints.data';
import { notificationEndpoints } from '../../notification/data/notification-endpoints.data';
import { projectEndpoints } from '../../project/data/project-endpoints.data';
import { taskEndpoints } from '../../task/data/task-endpoints.data';
import { userEndpoints } from '../../user/data/user-endpoints.data';

export const modules: ModuleDefinition[] = [
  {
    key: 'user',
    title: 'User Module',
    personName: 'Ilakiyaa',
    subtitle: 'Users and roles',
    description: 'Covers user CRUD operations and user-role mapping endpoints.',
    expectedAuthority: 'ROLE_USER_MODULE_DEV',
    validationPath: '/api/v1/users',
    endpoints: userEndpoints,
  },
  {
    key: 'project',
    title: 'Project Module',
    personName: 'Project Team',
    subtitle: 'Projects',
    description: 'Covers project CRUD endpoints.',
    expectedAuthority: 'ROLE_PROJECT_MODULE_DEV',
    validationPath: '/api/v1/projects',
    endpoints: projectEndpoints,
  },
  {
    key: 'collaboration',
    title: 'Collaboration Module',
    personName: 'Collaboration Team',
    subtitle: 'Comments and attachments',
    description: 'Covers task comments and task attachment endpoints.',
    expectedAuthority: 'ROLE_COMMENT_ATTACHMENT_MODULE_DEV',
    validationPath: '/api/v1/tasks/1/comments',
    endpoints: collaborationEndpoints,
  },
  {
    key: 'task',
    title: 'Task Module',
    personName: 'Sarmisha',
    subtitle: 'Tasks and categories',
    description: 'Covers task management, filters, and category mapping endpoints.',
    expectedAuthority: 'ROLE_TASK_MODULE_DEV',
    validationPath: '/api/v1/tasks',
    endpoints: taskEndpoints,
  },
  {
    key: 'notification',
    title: 'Notification Module',
    personName: 'Notification Team',
    subtitle: 'User notifications',
    description: 'Covers notification lookup endpoints.',
    expectedAuthority: 'ROLE_NOTIFICATION_MODULE_DEV',
    validationPath: '/api/v1/notifications/1',
    endpoints: notificationEndpoints,
  },
];

export function getModule(moduleKey: string): ModuleDefinition | undefined {
  return modules.find((module) => module.key === moduleKey);
}
