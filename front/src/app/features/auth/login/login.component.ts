import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';
import { AuthStateService } from 'src/app/core/services/auth-state.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder, 
    private authService: AuthService,
    private authStateService: AuthStateService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      identifier: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  goHome() {
  this.router.navigate(['/home']);
}

  onSubmit() {
    if (this.loginForm.invalid) return;

    const { identifier, password } = this.loginForm.value;

    const loginPayload = { emailOrUsername: identifier, password }
    

    this.authService.login(loginPayload).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        this.authStateService.setLoggedIn(true);
        this.router.navigate(['/posts']);
      },
      error: (error) => {
        console.error('Login failed', error);
      }
    });
  }
}
