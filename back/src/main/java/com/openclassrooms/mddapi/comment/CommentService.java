package com.openclassrooms.mddapi.comment;

import com.openclassrooms.mddapi.comment.DTO.CommentCreateDto;
import com.openclassrooms.mddapi.comment.DTO.CommentDto;
import com.openclassrooms.mddapi.user.User;
import com.openclassrooms.mddapi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service responsible for managing comments.
 * <p>
 * Handles business logic related to creating comments and retrieving
 * comments associated with posts.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;


    /**
     * Retrieves all comments associated with a given post.
     *
     * @param postId the identifier of the post
     * @return the list of comments for the specified post
     */
    public List<CommentDto> getCommentsForPost(Long postId) {
        return commentRepository.findAllByPostId(postId).stream().map(this::mapToDto).toList();
    }

    /**
     * Creates a new comment for a post.
     * <p>
     * The comment is associated with the authenticated user identified
     * by the provided email address.
     *
     * @param userEmail the email of the user creating the comment
     * @param dto the data required to create the comment
     * @return the created comment
     */
    public CommentDto createComment(String userEmail, CommentCreateDto dto) {
        Comment newComment = new Comment();
        User user = userService.getUserByEmail(userEmail);
        newComment.setContent(dto.getContent());
        newComment.setPostId(dto.getPostId());
        newComment.setAuthorId(user.getId());
        Comment savedComment = commentRepository.save(newComment);
        return mapToDto(savedComment);
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setAuthor(userService.getUserName(comment.getAuthorId()));
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }

}
