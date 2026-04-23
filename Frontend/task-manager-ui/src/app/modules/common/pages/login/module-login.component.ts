import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { getModule } from '../../data/module-definitions.data';
import { ModuleDefinition } from '../../models/app.models';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-module-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './module-login.component.html',
})
export class ModuleLoginComponent {
  private readonly formBuilder = inject(FormBuilder);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);

  readonly module: ModuleDefinition | undefined = getModule(this.route.snapshot.paramMap.get('moduleKey') ?? '');
  isSubmitting = false;
  errorMessage = '';

  readonly loginForm = this.formBuilder.nonNullable.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  onSubmit(): void {
    if (!this.module || this.loginForm.invalid) {
      return;
    }

    this.errorMessage = '';
    this.isSubmitting = true;

    const { username, password } = this.loginForm.getRawValue();

    this.authService.login(this.module.key, username, password).subscribe({
      next: () => {
        this.isSubmitting = false;
        void this.router.navigate(['/', this.module!.key, 'dashboard']);
      },
      error: (error) => {
        this.isSubmitting = false;

        if (error instanceof HttpErrorResponse && (error.status === 401 || error.status === 403)) {
          this.errorMessage = 'Invalid username or password.';
        } else if (error instanceof Error) {
          this.errorMessage = error.message;
        } else {
          this.errorMessage = 'Login failed. Please check the backend and try again.';
        }
      },
    });
  }
}
