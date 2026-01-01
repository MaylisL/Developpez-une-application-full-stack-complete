import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/services/auth.service';
import { Subject, SubjectsService } from '../subjects.service';

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss']
})
export class SubjectsComponent implements OnInit {

  subjects: Subject[] = [];
  subscribedSubjectsIds: number[] = [];

  constructor(
    private subjectService: SubjectsService,
    private authService: AuthService
  ) { }

 ngOnInit(): void {
    this.loadSubjects();
    this.loadSubscribedSubjects();
  }

  loadSubjects() {
    this.subjectService.getSubjects().subscribe({
      next: (data: Subject[]) => this.subjects = data,
      error: (err: Error) => console.error(err)
    });
  }

  loadSubscribedSubjects() {
    this.subjectService.getSubscribedSubjects().subscribe({
      next: (data: Subject[]) => this.subscribedSubjectsIds = data.map(s => s.id),
      error: (err: Error) => console.error(err)
    });
  }


  subscribe(subjectId: number) {
    this.subjectService.subscribeToSubject(subjectId).subscribe({
      next: (data: number) => this.subscribedSubjectsIds.push(data),
      error: (err: Error) => console.error(err)
    });
  }


  isSubscribed(subjectId: number): boolean {
    return this.subscribedSubjectsIds.includes(subjectId);
  }
}
