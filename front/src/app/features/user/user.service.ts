import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from '../subjects/subjects.service';
import { environment } from 'src/environments/environment';

export interface UserUpdate {
  username?: string;
  email?: string;
  password?: string;
}

export interface UserProfile {
  username: string;
  email: string;
  subscriptions: Subject[]
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private apiUrl = `${environment.apiUrl}/user`;

  constructor(private http: HttpClient) { }

  update(userData: UserUpdate): Observable<{token: string}> {
    return this.http.put<{token: string}>(`${this.apiUrl}/update`, userData);
  }

  getProfile(): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.apiUrl}/me`);
  }

}
