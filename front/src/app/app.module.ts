import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './features/home/home.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './core/interceptors/auth.interceptor';
import { ReactiveFormsModule } from '@angular/forms';
import { NavbarComponent } from './shared/components/navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule, MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { LoginComponent } from './features/auth/login/login.component';
import { MatCardModule } from '@angular/material/card';
import { RegisterComponent } from './features/auth/register/register.component';
import { AuthService } from './core/services/auth.service';
import { PostsComponent } from './features/posts/posts-list/posts-list.component';
import { ButtonComponent } from './shared/components/button/button.component';
import { CardComponent } from './shared/components/card/card.component';
import { PostCreateComponent } from './features/posts/post-create/post-create.component';
import { SubjectsComponent } from './features/subjects/subjects/subjects.component';
import { UserComponent } from './features/user/user.component';
import { PostDetailComponent } from './features/posts/post-detail/post-detail.component';
import { MatSidenavModule } from '@angular/material/sidenav';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    PostsComponent,
    ButtonComponent,
    CardComponent,
    PostCreateComponent,
    SubjectsComponent,
    UserComponent,
    PostDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatCardModule,
    MatSidenavModule
  ],
  providers: [
     AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
  constructor(private iconRegistry: MatIconRegistry, private sanitizer: DomSanitizer) {
     iconRegistry.addSvgIcon(
      'profile', 
      sanitizer.bypassSecurityTrustResourceUrl('assets/icons/profile.svg')
    );
  }
}
