import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface CreateComment {
  content: string;
  postId: number;
}

export interface Comment {
  content: string;
  author: string;
  createdAt: string
}

@Injectable({
  providedIn: 'root'
})
export class CommentService {

   private apiUrl = 'http://localhost:8080/api/comments';
  

  constructor(private http: HttpClient) { }

  createComment(commentData: CreateComment): Observable<any> {
    return this.http.post(this.apiUrl, commentData);
  }

  getCommentsByPostId(postId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiUrl}/${postId}`)
  }


}
