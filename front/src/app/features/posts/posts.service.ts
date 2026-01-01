
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from 'src/app/core/services/comment.service';
import { environment } from 'src/environments/environment';

export interface Post {
  title: string;
  content: string;
}

export interface PostListView {
  id: number;
  title: string;
  content: string;
  author: string;
  createdAt: string;
}

export interface PostDetailsView extends PostListView {
  subject: string;
  comments: Comment[]
}


@Injectable({
  providedIn: 'root'
})
export class PostsService {

  private apiUrl = `${environment.apiUrl}/posts`;

  constructor(private http: HttpClient) { }

  createPost(post: Post): Observable<any> {
    return this.http.post(this.apiUrl, post);
  }

  getUserPosts(): Observable<PostListView[]> {
    return this.http.get<PostListView[]>(this.apiUrl);
  }

  getPostDetails(postId: number): Observable<PostDetailsView> {
    return this.http.get<PostDetailsView>(`${this.apiUrl}/${postId}`);
  }
}
