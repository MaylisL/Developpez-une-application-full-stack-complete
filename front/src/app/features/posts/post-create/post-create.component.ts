import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PostsService } from '../../posts/posts.service';
import { SubjectsService } from '../../subjects/subjects.service';
import { Subject } from '../../subjects/subjects.service';

@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.scss']
})
export class PostCreateComponent implements OnInit {

  postForm!: FormGroup;
  subjects: Subject[] = [];

  constructor(
    private fb: FormBuilder,
    private postsService: PostsService,
    private subjectsService: SubjectsService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.postForm = this.fb.group({
      subjectId: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required]
    });

    this.subjectsService.getSubjects().subscribe({
      next: (data) => (this.subjects = data),
      error: (err) => console.error(err)
    });
  }

  onSubmit(): void {
    if (this.postForm.valid) {
      this.postsService.createPost(this.postForm.value).subscribe({
        next: () => this.router.navigate(['/posts']),
        error: (err) => console.error('Erreur création post', err)
      });
    }
  }
}
