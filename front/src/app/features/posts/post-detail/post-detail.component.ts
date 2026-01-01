import { Component, OnDestroy, OnInit } from '@angular/core';
import { PostDetailsView, PostsService } from '../posts.service';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Subject, switchMap, takeUntil } from 'rxjs';
import { CommentService, CreateComment } from 'src/app/core/services/comment.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss']
})
export class PostDetailComponent implements OnInit, OnDestroy {

  post: PostDetailsView | null = null;
  commentForm: FormGroup;
  postId: number | null = null;
  error: string | null = null;
  private destroy$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private postsService: PostsService,
    private commentService: CommentService,
    private fb: FormBuilder
  ) { 
    this.commentForm = this.fb.group({
      commentContent: ['', [Validators.required, Validators.minLength(1)]],
    });

  }

  ngOnInit(): void {
    
    this.route.paramMap.pipe(
      takeUntil(this.destroy$),
      switchMap(params => {
        const postId = Number(params.get('id'));
        this.postId = postId;
        this.error = null;

        return forkJoin({
          postData: this.postsService.getPostDetails(postId),
          comments: this.commentService.getCommentsByPostId(postId)
        });
      })
    ).subscribe({
        next: (data) => {
          this.post = data.postData;
          this.post.comments = data.comments;
        },
        error: (err) => {
          this.post = null;
          this.error = 'Erreur lors du chargement de données';
        }
    });
  }

  addComment() {
    if (this.commentForm.valid) {
      const commentPayload: CreateComment =  {
        content: this.commentForm.value.commentContent,
        postId: this.postId as number 
      }

      this.commentService.createComment(commentPayload).subscribe({
        next: (comment) => {
          console.log('Comment added successfully');
          this.post?.comments.push(comment);
          this.commentForm.reset();
        },
        error: (err) => console.error('Error posting comment:', err),
      });
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
