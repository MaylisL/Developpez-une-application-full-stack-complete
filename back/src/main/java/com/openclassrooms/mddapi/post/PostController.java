package com.openclassrooms.mddapi.post;

import com.openclassrooms.mddapi.post.DTO.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing posts.
 * <p>
 * Provides endpoints for creating posts, retrieving posts
 * subscribed to the authenticated user, and accessing post details.
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * Creates a new post.
     * <p>
     * The post is associated with the authenticated user extracted
     * from the JWT token.
     *
     * @param dto the data required to create a post
     * @param jwt the JWT token representing the authenticated user
     * @return HTTP 201 (Created) if the post is successfully created
     */
    @PostMapping
    public ResponseEntity<Void> createPost(@Valid @RequestBody PostCreateDto dto,
                                           @AuthenticationPrincipal Jwt jwt) {
        String connectedUserEmail = jwt.getSubject();

        postService.createPost(dto, connectedUserEmail);
        return ResponseEntity.status(201).build();
    }

    /**
     * Retrieves all posts that are of a subject(Theme) subscribed to the authenticated user.
     *
     * @param jwt the JWT token representing the authenticated user
     * @return the list of posts the user is subscribed to
     */
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllSubscribedPosts(@AuthenticationPrincipal Jwt jwt) {
        String connectedUserEmail = jwt.getSubject();
        List<PostDto> postDtoList = postService.getSubscribedPosts(connectedUserEmail);
        return ResponseEntity.ok(postDtoList);
    }

    /**
     * Retrieves the details of a specific post.
     *
     * @param id the identifier of the post
     * @return the detailed information of the requested post
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }
}
