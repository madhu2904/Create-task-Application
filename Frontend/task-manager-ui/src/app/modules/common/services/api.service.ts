import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom, timeout } from 'rxjs';

import { Category, EndpointDefinition, InputFieldDefinition, Role } from '../models/app.models';
import { AuthService } from './auth.service';

const API_BASE_URL = 'http://localhost:8081';
const REQUEST_TIMEOUT_MS = 15000;

interface ApiResponse<T> {
  data: T;
}

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);

  executeEndpoint(endpoint: EndpointDefinition, values: Record<string, string>): Promise<unknown> {
    const url = this.buildUrl(endpoint, values);
    const headers = this.createHeaders();
    const body = this.buildBody(endpoint.bodyFields ?? [], values);

    switch (endpoint.method) {
      case 'GET':
        return firstValueFrom(this.http.get(url, { headers }).pipe(timeout(REQUEST_TIMEOUT_MS)));
      case 'POST':
        return firstValueFrom(this.http.post(url, body, { headers }).pipe(timeout(REQUEST_TIMEOUT_MS)));
      case 'PUT':
        return firstValueFrom(this.http.put(url, body, { headers }).pipe(timeout(REQUEST_TIMEOUT_MS)));
      case 'DELETE':
        return firstValueFrom(this.http.delete(url, { headers }).pipe(timeout(REQUEST_TIMEOUT_MS)));
    }
  }

  getRoles(): Promise<Role[]> {
    return firstValueFrom(
      this.http.get<ApiResponse<Role[]>>(`${API_BASE_URL}/api/v1/roles`, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)),
    ).then((response) => response.data ?? []);
  }

  getCategories(): Promise<Category[]> {
    return firstValueFrom(
      this.http.get<ApiResponse<Category[]>>(`${API_BASE_URL}/api/v1/categories`, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)),
    ).then((response) => response.data ?? []);
  }

  createUser(values: Record<string, string>): Promise<unknown> {
    const body = {
      username: values['username'],
      password: values['password'],
      email: values['email'],
      fullName: values['fullName'],
      roles: [],
    };

    return firstValueFrom(this.http.post(`${API_BASE_URL}/api/v1/users`, body, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)));
  }

  getUserById(userId: number): Promise<unknown> {
    return firstValueFrom(this.http.get(`${API_BASE_URL}/api/v1/users/${userId}`, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)));
  }

  getUserRoles(userId: number): Promise<Role[]> {
    return firstValueFrom(
      this.http.get<ApiResponse<Role[]>>(`${API_BASE_URL}/api/v1/users/${userId}/roles`, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)),
    ).then((response) => response.data ?? []);
  }

  assignRoleToUser(userId: number, roleId: number): Promise<unknown> {
    return firstValueFrom(
      this.http.post(`${API_BASE_URL}/api/v1/users/${userId}/roles/${roleId}`, null, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)),
    );
  }

  removeRoleFromUser(userId: number, roleId: number): Promise<unknown> {
    return firstValueFrom(
      this.http.delete(`${API_BASE_URL}/api/v1/users/${userId}/roles/${roleId}`, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)),
    );
  }

  createTask(values: Record<string, string>, categoryIds: number[] = []): Promise<unknown> {
    const body = {
      taskName: values['taskName'],
      description: values['description'],
      priority: values['priority'],
      status: values['status'],
      dueDate: this.toDateTimeValue(values['dueDate']),
      projectId: Number(values['projectId']),
      userId: Number(values['userId']),
      categoryIds,
    };

    return firstValueFrom(this.http.post(`${API_BASE_URL}/api/v1/tasks`, body, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)));
  }

  updateTask(taskId: number, values: Record<string, string>, categoryIds: number[] = []): Promise<unknown> {
    const body = {
      taskName: values['taskName'],
      description: values['description'],
      priority: values['priority'],
      status: values['status'],
      dueDate: this.toDateTimeValue(values['dueDate']),
      projectId: Number(values['projectId']),
      userId: Number(values['userId']),
      categoryIds,
    };

    return firstValueFrom(this.http.put(`${API_BASE_URL}/api/v1/tasks/${taskId}`, body, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)));
  }

  getTaskById(taskId: number): Promise<unknown> {
    return firstValueFrom(this.http.get(`${API_BASE_URL}/api/v1/tasks/${taskId}`, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)));
  }

  getTaskCategories(taskId: number): Promise<Category[]> {
    return firstValueFrom(
      this.http.get<ApiResponse<Category[]>>(`${API_BASE_URL}/api/v1/tasks/${taskId}/categories`, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)),
    ).then((response) => response.data ?? []);
  }

  addCategoryToTask(taskId: number, categoryId: number): Promise<unknown> {
    return firstValueFrom(
      this.http.post(`${API_BASE_URL}/api/v1/tasks/${taskId}/categories/${categoryId}`, null, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)),
    );
  }

  removeCategoryFromTask(taskId: number, categoryId: number): Promise<unknown> {
    return firstValueFrom(
      this.http.delete(`${API_BASE_URL}/api/v1/tasks/${taskId}/categories/${categoryId}`, { headers: this.createHeaders() }).pipe(timeout(REQUEST_TIMEOUT_MS)),
    );
  }

  toErrorMessage(error: unknown): string {
    if (!(error instanceof HttpErrorResponse)) {
      return 'The API request did not finish. Please check whether the backend is running and reachable.';
    }

    const serverMessage =
      typeof error.error === 'string'
        ? error.error
        : error.error?.message || error.error?.error || error.error?.data || error.message;

    return `Request failed with status ${error.status}: ${serverMessage}`;
  }

  private buildUrl(endpoint: EndpointDefinition, values: Record<string, string>): string {
    let resolvedPath = endpoint.path;

    for (const field of endpoint.pathParams ?? []) {
      resolvedPath = resolvedPath.replace(`{${field.key}}`, encodeURIComponent(values[field.key] ?? ''));
    }

    let params = new HttpParams();
    for (const field of endpoint.queryParams ?? []) {
      const value = values[field.key]?.trim();
      if (value) {
        params = params.set(field.key, value);
      }
    }

    const queryString = params.toString();
    return `${API_BASE_URL}${resolvedPath}${queryString ? `?${queryString}` : ''}`;
  }

  private buildBody(fields: InputFieldDefinition[], values: Record<string, string>): Record<string, unknown> | null {
    if (fields.length === 0) {
      return null;
    }

    return fields.reduce<Record<string, unknown>>((payload, field) => {
      const rawValue = values[field.key];

      if (rawValue === undefined || rawValue === '') {
        return payload;
      }

      payload[field.key] = this.parseValue(field, rawValue);
      return payload;
    }, {});
  }

  private parseValue(field: InputFieldDefinition, value: string): number | string {
    if (field.type === 'number') {
      return Number(value);
    }

    if (field.type === 'datetime-local') {
      return this.toDateTimeValue(value);
    }

    return value;
  }

  private toDateTimeValue(value: string | undefined): string {
    if (!value) {
      return '';
    }

    return value.length === 16 ? `${value}:00` : value;
  }

  private createHeaders(): HttpHeaders {
    const authHeader = this.authService.session?.authHeader ?? '';

    return new HttpHeaders({
      Authorization: authHeader,
      'Content-Type': 'application/json',
    });
  }
}
