import { Component, OnInit } from '@angular/core';
import { PostListView, PostsService } from '../posts.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts-list.component.html',
  styleUrls: ['./posts-list.component.scss']
})
export class PostsComponent implements OnInit {
  posts: PostListView[] = [];
  sortOrder: 'asc' | 'desc' = 'desc';

  constructor(private postsService: PostsService) { }

  ngOnInit(): void {
     this.loadPosts();
  }

  loadPosts(): void {
    this.postsService.getUserPosts().subscribe({
      next: (data) => {
        this.posts = this.sortPosts(data);
      },
      error: (err) => console.error('Error fetching posts:', err)
    });
  }

  toggleSortOrder(): void {
    this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
    this.posts = this.sortPosts(this.posts);
  }

   private sortPosts(posts: PostListView[]): PostListView[] {
    return posts.sort((a, b) => {
      const dateA = this.parseDate(a.createdAt).getTime();
      const dateB = this.parseDate(b.createdAt).getTime();
      return this.sortOrder === 'asc' ? dateA - dateB : dateB - dateA;
    });
  }

  private parseDate(dateString: string): Date {
    const [day, month, year] = dateString.split('/').map(datePart => parseInt(datePart, 10));
    return new Date(year, month - 1, day);
  }

}
