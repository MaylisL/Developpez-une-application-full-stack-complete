import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthStateService {
  private loggedInSubject = new BehaviorSubject<boolean>(this.hasToken());
  loggedIn$ = this.loggedInSubject.asObservable();

  private hasToken() {
    return !!localStorage.getItem('token');
  }

  setLoggedIn(value: boolean) {
    this.loggedInSubject.next(value);
  }
}
