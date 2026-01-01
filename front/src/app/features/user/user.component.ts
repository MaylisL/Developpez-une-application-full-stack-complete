import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserProfile, UserService } from './user.service';
import { Subject, SubjectsService } from '../subjects/subjects.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent {

  userForm: FormGroup;
  passwordRegex: RegExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^a-zA-Z\d]).+$/;
  subscriptions: Subject[] = [];
  error: string | null = null;

     constructor(
      private fb: FormBuilder,
      private userService: UserService,
      private subjectsService: SubjectsService
      ) {
        this.userForm = this.fb.group({
        username: ['', [Validators.minLength(3)]],
        email: ['', [Validators.email]],
        password: ['', [
          Validators.minLength(8),
          Validators.pattern(this.passwordRegex),
        ]],
      });
        this.userService.getProfile().subscribe({
          next: (data) => {
            this.error = null;
            this.initUserPage(data);
          },
          error: (err) => {
            console.error('Erreur pendant la récupération du profil', err)
            this.error = 'Erreur pendant la récupération du profil'
          }
        });

    }

    initUserPage(data: UserProfile) {
      this.userForm.patchValue({
        username: data.username,
        email: data.email,
      });
      this.subscriptions = data.subscriptions;
    }


    onSubmit() {
      if (this.userForm.valid) {
          const formData = this.userForm.value;

          this.userService.update(formData).subscribe({
            next: (response) => {
              this.error = null;
              localStorage.clear();
              localStorage.setItem('token', response.token);
            },
            error: (error) => {
              console.error('Échec de la mise à jour du profil', error);
              this.error = 'Échec de la mise à jour du profil';
            }
          });

      }
    }

    unsubscribe(subjectId: number) {
      this.subjectsService.unsubscribeFromSubject(subjectId).subscribe({
        next: () => {
          this.removeSubscriptionFromList(subjectId);
        },
        error: (err) => console.error("Error pendant unsubscribe", err)
      });
    }

    private removeSubscriptionFromList(subjectId: number) {
      this.subscriptions = this.subscriptions.filter(sub => sub.id !== subjectId);
    }

  }
