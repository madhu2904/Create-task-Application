import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

import { AuthService } from '../services/auth.service';

export const moduleAuthGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const moduleKey = route.data['moduleKey'] as string | undefined;

  if (moduleKey && authService.isAuthenticatedForModule(moduleKey)) {
    return true;
  }

  return router.createUrlTree(['/modules', moduleKey ?? '', 'login']);
};
