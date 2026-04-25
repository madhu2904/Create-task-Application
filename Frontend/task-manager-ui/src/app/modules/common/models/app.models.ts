export type FieldType = 'text' | 'number' | 'email' | 'date' | 'datetime-local' | 'textarea';

export interface InputFieldDefinition {
  key: string;
  label: string;
  type: FieldType;
  required?: boolean;
  placeholder?: string;
}

export interface EndpointDefinition {
  key: string;
  title: string;
  description: string;
  method: 'GET' | 'POST' | 'PUT' | 'DELETE';
  path: string;
  pathParams?: InputFieldDefinition[];
  queryParams?: InputFieldDefinition[];
  bodyFields?: InputFieldDefinition[];
}

export interface ModuleDefinition {
  key: string;
  title: string;
  personName: string;
  subtitle: string;
  description: string;
  username?: string;
  passwordHint?: string;
  expectedAuthority: string;
  endpoints: EndpointDefinition[];
}

export interface AuthSession {
  moduleKey: string;
  username: string;
  authHeader: string;
}

export interface AuthVerificationResponse {
  authenticated: boolean;
  username: string;
  authorities: string[];
}
//used to define the module devs roles 
export interface Role {
  userRoleId?: number;
  roleId?: number;
  id?: number;
  roleName?: string;
  name?: string;
}

export interface Category {
  categoryId?: number;
  id?: number;
  categoryName?: string;
  name?: string;
}

export interface ResponseCardRow {
  label: string;
  value?: string;
  bullets?: string[];
}

export interface ResponseDisplayCard {
  kind: 'summary' | 'data';
  title: string;
  subtitle?: string;
  badge?: string;
  highlight?: { label: string; value: string };
  rows: ResponseCardRow[];
}
