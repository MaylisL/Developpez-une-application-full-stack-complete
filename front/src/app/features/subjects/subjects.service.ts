import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface Subject {
  id: number;
  title: string;
  description: string;
}

@Injectable({
  providedIn: 'root'
})
export class SubjectsService {

  private apiUrl = `${environment.apiUrl}/subjects`;

  constructor(private http: HttpClient) { }

  getSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(this.apiUrl);
  }

  subscribeToSubject(subjectId: number): Observable<number> {
   return this.http.post<number>(`${this.apiUrl}/${subjectId}/subscribe`, {});
  }

  unsubscribeFromSubject(subjectId: number): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/${subjectId}/unsubscribe`, {});
  }

  getSubscribedSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(this.apiUrl + '/subscribed');
  }
}
