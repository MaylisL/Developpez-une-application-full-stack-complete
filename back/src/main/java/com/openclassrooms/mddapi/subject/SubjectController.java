package com.openclassrooms.mddapi.subject;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing subjects.
 * <p>
 * Provides endpoints for retrieving all subjects, subscribing or unsubscribing
 * from a subject, and retrieving the subjects the authenticated user is subscribed to.
 */
@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    /**
     * Retrieves all available subjects.
     *
     * @return the list of all subjects
     */
    @GetMapping
    public List<SubjectDto> getSubjects() {
        return subjectService.getAllSubjects();
    }


    /**
     * Subscribes the authenticated user to a specific subject.
     *
     * @param id the identifier of the subject to subscribe to
     * @param jwt the JWT token representing the authenticated user
     * @return the ID of the subject the user was subscribed to
     */
    @PostMapping("/{id}/subscribe")
    public ResponseEntity<Long> subscribeToSubject(@PathVariable Long id,
                                                   @AuthenticationPrincipal Jwt jwt) {
        String connectedUserEmail = jwt.getSubject();
        subjectService.subscribeToSubject(connectedUserEmail, id);
        return ResponseEntity.ok(id);
    }

    /**
     * Unsubscribes the authenticated user from a specific subject.
     *
     * @param id the identifier of the subject to unsubscribe from
     * @param jwt the JWT token representing the authenticated user
     * @return the ID of the subject the user was unsubscribed from
     */
    @PostMapping("/{id}/unsubscribe")
    public ResponseEntity<Long> unsubscribeFromSubject(@PathVariable Long id,
                                                   @AuthenticationPrincipal Jwt jwt) {
        String connectedUserEmail = jwt.getSubject();
        subjectService.unsubscribeFromSubject(connectedUserEmail, id);
        return ResponseEntity.ok(id);
    }

    /**
     * Retrieves all subjects that the authenticated user is subscribed to.
     *
     * @param jwt the JWT token representing the authenticated user
     * @return the list of subjects the user is subscribed to
     */
    @GetMapping("/subscribed")
    public List<SubjectDto> getSubscribedSubjects(@AuthenticationPrincipal Jwt jwt) {
        return subjectService.getUserSubscribedSubjects(jwt.getSubject());
    }
}
