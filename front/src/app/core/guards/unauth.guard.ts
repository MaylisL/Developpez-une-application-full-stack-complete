import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { map, Observable, take } from 'rxjs';
import { AuthStateService } from '../services/auth-state.service';

@Injectable({
  providedIn: 'root'
})
export class UnauthGuard implements CanActivate {
  constructor(
    private authStateService: AuthStateService,
    private router: Router
  ) { }

  canActivate(): Observable<boolean> {
    return this.authStateService.loggedIn$.pipe(
      take(1),
      map(isLoggedIn => {
        if (isLoggedIn) {
          this.router.navigate(['/posts']);
          return false;
        }
        return true
      })
    )
  }

}
