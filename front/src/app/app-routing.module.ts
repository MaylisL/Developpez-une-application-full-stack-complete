import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { LoginComponent } from './features/auth/login/login.component';
import { PostsComponent } from './features/posts/posts-list/posts-list.component';
import { PostCreateComponent } from './features/posts/post-create/post-create.component';
import { SubjectsComponent } from './features/subjects/subjects/subjects.component';
import { UserComponent } from './features/user/user.component';
import { PostDetailComponent } from './features/posts/post-detail/post-detail.component';
import { AuthGuard } from './core/guards/auth.guard';
import { UnauthGuard } from './core/guards/unauth.guard';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [UnauthGuard] },
  { path: 'login', component: LoginComponent, canActivate: [UnauthGuard]},
  { path: 'register', component: RegisterComponent, canActivate: [UnauthGuard] },
  { path: 'posts', component: PostsComponent, canActivate: [AuthGuard]},
  { path: 'posts/create', component: PostCreateComponent, canActivate: [AuthGuard] },
  { path: 'posts/:id', component: PostDetailComponent, canActivate: [AuthGuard] },
  { path: 'subjects', component: SubjectsComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: UserComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
