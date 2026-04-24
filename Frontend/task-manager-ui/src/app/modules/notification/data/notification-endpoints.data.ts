import { EndpointDefinition } from '../../common/models/app.models';

export const notificationEndpoints: EndpointDefinition[] = [
  {
    key: 'create-notification',
    title: 'Create Notification',
    description: 'Create a notification for a user.',
    method: 'POST',
    path: '/api/v1/notifications',
    bodyFields: [
      { key: 'text', label: 'Text', type: 'textarea', required: true, placeholder: 'Notification message' },
      { key: 'userId', label: 'User Id', type: 'number', required: true, placeholder: '1' },
    ],
  },
  {
    key: 'get-notification-by-id',
    title: 'Find Notification By Id',
    description: 'Fetch one notification by id.',
    method: 'GET',
    path: '/api/v1/notifications/{notificationId}',
    pathParams: [{ key: 'notificationId', label: 'Notification Id', type: 'number', required: true, placeholder: '1' }],
  },
  {
    key: 'get-user-notifications',
    title: 'Get User Notifications',
    description: 'Fetch notifications for a user.',
    method: 'GET',
    path: '/api/v1/notifications/users/{userId}/notifications',
    pathParams: [{ key: 'userId', label: 'User Id', type: 'number', required: true, placeholder: '1' }],
  },
  {
    key: 'delete-notification',
    title: 'Delete Notification',
    description: 'Delete a notification by id.',
    method: 'DELETE',
    path: '/api/v1/notifications/{notificationId}',
    pathParams: [{ key: 'notificationId', label: 'Notification Id', type: 'number', required: true, placeholder: '1' }],
  },
  {
    key: 'get-unread-notifications',
    title: 'Get Unread Notifications',
    description: 'Fetch unread notifications for a user.',
    method: 'GET',
    path: '/api/v1/notifications/users/{userId}/notifications/unread',
    pathParams: [{ key: 'userId', label: 'User Id', type: 'number', required: true, placeholder: '1' }],
  },
  {
    key: 'mark-notification-read',
    title: 'Mark Notification Read',
    description: 'Mark a notification as read.',
    method: 'PUT',
    path: '/api/v1/notifications/{notificationId}/read',
    pathParams: [{ key: 'notificationId', label: 'Notification Id', type: 'number', required: true, placeholder: '1' }],
  },
];
