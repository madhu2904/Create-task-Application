import { EndpointDefinition } from '../../common/models/app.models';

export const collaborationEndpoints: EndpointDefinition[] = [
  {
    key: 'create-comment',
    title: 'Create Comment',
    description: 'Add a comment to a task.',
    method: 'POST',
    path: '/api/v1/tasks/{taskId}/comments',
    pathParams: [{ key: 'taskId', label: 'Task Id', type: 'number', required: true, placeholder: '1' }],
    bodyFields: [
      { key: 'text', label: 'Comment Text', type: 'textarea', required: true, placeholder: 'Looks good to me' },
      { key: 'userId', label: 'User Id', type: 'number', required: true, placeholder: '1' },
    ],
  },
  {
    key: 'get-task-comments',
    title: 'Get Task Comments',
    description: 'Fetch comments for a task.',
    method: 'GET',
    path: '/api/v1/tasks/{taskId}/comments',
    pathParams: [{ key: 'taskId', label: 'Task Id', type: 'number', required: true, placeholder: '1' }],
  },
  {
    key: 'delete-comment',
    title: 'Delete Comment',
    description: 'Delete a comment by id.',
    method: 'DELETE',
    path: '/api/v1/comments/{commentId}',
    pathParams: [{ key: 'commentId', label: 'Comment Id', type: 'number', required: true, placeholder: '1' }],
  },
  {
    key: 'create-attachment',
    title: 'Create Attachment',
    description: 'Attach a file reference to a task.',
    method: 'POST',
    path: '/api/v1/tasks/{taskId}/attachments',
    pathParams: [{ key: 'taskId', label: 'Task Id', type: 'number', required: true, placeholder: '1' }],
    bodyFields: [
      { key: 'fileName', label: 'File Name', type: 'text', required: true, placeholder: 'spec.pdf' },
      { key: 'filePath', label: 'File Path', type: 'text', required: true, placeholder: '/files/spec.pdf' },
    ],
  },
  {
    key: 'get-task-attachments',
    title: 'Get Task Attachments',
    description: 'Fetch attachments for a task.',
    method: 'GET',
    path: '/api/v1/tasks/{taskId}/attachments',
    pathParams: [{ key: 'taskId', label: 'Task Id', type: 'number', required: true, placeholder: '1' }],
  },
  {
    key: 'delete-attachment',
    title: 'Delete Attachment',
    description: 'Delete an attachment by id.',
    method: 'DELETE',
    path: '/api/v1/attachments/{attachmentId}',
    pathParams: [{ key: 'attachmentId', label: 'Attachment Id', type: 'number', required: true, placeholder: '1' }],
  },
];
