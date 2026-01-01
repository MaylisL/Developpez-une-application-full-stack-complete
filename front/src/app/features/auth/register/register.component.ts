import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { AuthStateService } from 'src/app/core/services/auth-state.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent{
  registerForm: FormGroup;
  passwordRegex: RegExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^a-zA-Z\d]).+$/;
  error: string | null = null;
  constructor(
    private fb: FormBuilder, 
    private router: Router,
    private authService: AuthService,
    private autthStateService: AuthStateService
    ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required, 
        Validators.minLength(8), 
        Validators.pattern(this.passwordRegex),
      ]],
    });
  }

  goHome() {
  this.router.navigate(['/home']);
  }

  onSubmit() {
    if (this.registerForm.valid) {
        const formData = this.registerForm.value;

        this.authService.register(formData).subscribe({
          next: (response) => {
            this.error = null;
            localStorage.setItem('token', response.token);
            this.autthStateService.setLoggedIn(true);
            this.router.navigate(['/']);
          },
          error: (error) => {
            console.error('Registration failed', error);
            this.error = "Échec de l'inscription";
          }
        });

    }
  }
}
