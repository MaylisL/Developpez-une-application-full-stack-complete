import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { AuthStateService } from './auth-state.service';
import { environment } from 'src/environments/environment';

interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}

interface LoginRequest {
  emailOrUsername: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;

  constructor(
    private http: HttpClient,
    private authStateService: AuthStateService,
    private router: Router
  ) { }

  register(data: RegisterRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, data);
  }

  login(data: LoginRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, data);
  }

  logout(): void {
    localStorage.clear();
    this.authStateService.setLoggedIn(false);
    this.router.navigate(['/']);
  }
}
