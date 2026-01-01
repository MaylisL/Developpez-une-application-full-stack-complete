package com.openclassrooms.mddapi.comment;


import com.openclassrooms.mddapi.comment.DTO.CommentCreateDto;
import com.openclassrooms.mddapi.comment.DTO.CommentDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing comments.
 * <p>
 * Provides endpoints for creating comments and retrieving comments
 * associated with a specific post.
 */
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * Creates a new comment for a post.
     * <p>
     * The authenticated user's email is extracted from the JWT token
     * and used as the author of the comment.
     *
     * @param dto the data required to create a comment
     * @param jwt the JWT token representing the authenticated user
     * @return the created comment
     */
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentCreateDto dto,
                                              @AuthenticationPrincipal Jwt jwt) {

        String connectedUserEmail = jwt.getSubject();

        return ResponseEntity.ok(commentService.createComment(connectedUserEmail, dto));

    }

    /**
     * Retrieves all comments associated with a given post.
     *
     * @param postId the identifier of the post
     * @return the list of comments for the specified post
     */
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long postId) {

        return ResponseEntity.ok(commentService.getCommentsForPost(postId));
    }
}
