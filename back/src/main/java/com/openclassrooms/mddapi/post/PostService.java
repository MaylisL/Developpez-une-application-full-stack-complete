package com.openclassrooms.mddapi.post;

import com.openclassrooms.mddapi.core.exception.PostNotFoundException;
import com.openclassrooms.mddapi.core.exception.UserNotFoundException;
import com.openclassrooms.mddapi.post.DTO.*;
import com.openclassrooms.mddapi.subject.Subject;
import com.openclassrooms.mddapi.subject.SubjectRepository;
import com.openclassrooms.mddapi.user.UserRepository;
import com.openclassrooms.mddapi.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service responsible for managing posts.
 * <p>
 * Handles post creation, retrieval of posts subscribed to a user,
 * and access to detailed post information.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new post authored by the specified user.
     * <p>
     * The post is associated with an existing subject and persisted
     * in the database.
     *
     * @param dto the data required to create the post
     * @param email the email of the user creating the post
     * @throws java.util.NoSuchElementException if user or subject does not exists
     */
    public void createPost(PostCreateDto dto, String email) {
        User author = userRepository.findByEmail(email).orElseThrow();
        Subject subject = subjectRepository.findById(dto.getSubjectId()).orElseThrow();

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(author)
                .subject(subject)
                .build();

        postRepository.save(post);
    }

    /**
     * Retrieves all posts associated with subjects the user is subscribed to.
     *
     * @param userEmail the email of the user
     * @return the list of posts related to the user's subscribed subjects
     * @throws UserNotFoundException if the user does not exist
     */
    public List<PostDto> getSubscribedPosts(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        Set<Subject> subscribedSubjects = user.getSubscribedSubjects();

        if (subscribedSubjects.isEmpty()) {
            return Collections.emptyList();
        }

        List<Post> posts = postRepository.findBySubjectIn(subscribedSubjects);

        return posts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the detailed information of a post by its identifier.
     *
     * @param id the identifier of the post
     * @return detailed post information
     * @throws PostNotFoundException if no post exists with the given ID
     */
    public PostDetailDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        return mapToDetailDto(post);
    }


    private PostDto mapToDto(Post post) {

        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthor(post.getAuthor().getUsername());
        dto.setCreatedAt(post.getCreatedAt());
        return dto;
    }

    private PostDetailDto mapToDetailDto(Post post) {
        PostDetailDto dto = new PostDetailDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthor(post.getAuthor().getUsername());
        dto.setSubject(post.getSubject().getName());
        dto.setCreatedAt(post.getCreatedAt());
        return dto;
    }
}
