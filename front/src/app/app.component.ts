import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter, map, take } from 'rxjs';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';

  showNavbar = true;
  hideMenu = false;

  private noMenuRoutes = ['/login', '/register'];

  constructor(private router: Router, private authService: AuthService) {

    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        const currentUrl = event.urlAfterRedirects;

        // Hide navbar ONLY on home/empty route
        this.showNavbar = !(currentUrl === '/' || currentUrl === '');
        this.hideMenu = this.noMenuRoutes.includes(currentUrl);
      });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

}
