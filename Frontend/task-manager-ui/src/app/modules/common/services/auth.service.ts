import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map, tap } from 'rxjs';

import { getModule } from '../data/module-definitions.data';
import { AuthSession, AuthVerificationResponse } from '../models/app.models';

const API_BASE_URL = 'http://localhost:8081';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private currentSession: AuthSession | null = null;

  get session(): AuthSession | null {
    return this.currentSession;
  }

  login(moduleKey: string, username: string, password: string): Observable<AuthSession> {
    this.clearSession();

    const token = btoa(`${username}:${password}`);
    const authHeader = `Basic ${token}`;
    const headers = new HttpHeaders({ Authorization: authHeader });

    return this.http.get<AuthVerificationResponse>(`${API_BASE_URL}/api/v1/auth/verify`, { headers }).pipe(
      map((response) => {
        const module = getModule(moduleKey);
        if (module && !response.authorities.includes(module.expectedAuthority)) {
          throw new Error('These credentials do not have access to this module.');
        }

        return {
          moduleKey,
          username: response.username || username,
          authHeader,
        };//session
      }),
      tap((session) => {
        this.currentSession = session;
      }),
    );
  }

  clearSession(): void {
    this.currentSession = null;
  }

  isAuthenticatedForModule(moduleKey: string): boolean {
    return this.currentSession?.moduleKey === moduleKey;
  }
}
