package com.openclassrooms.mddapi.subject;

import com.openclassrooms.mddapi.core.exception.SubjectNotFoundException;
import com.openclassrooms.mddapi.core.exception.UserNotFoundException;
import com.openclassrooms.mddapi.user.User;
import com.openclassrooms.mddapi.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for managing subjects and user subscriptions.
 * <p>
 * Provides operations for retrieving subjects, subscribing and unsubscribing
 * users to subjects, and retrieving the subjects a user is subscribed to.
 */
@Service
@RequiredArgsConstructor
public class SubjectService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    /**
     * Retrieves all available subjects.
     *
     * @return the list of all subjects
     */
    @Transactional
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Subscribes a user to a subject.
     *
     * @param userEmail the email of the user subscribing to the subject
     * @param subjectId the identifier of the subject
     * @throws UserNotFoundException if the user does not exist
     * @throws SubjectNotFoundException if the subject does not exist
     */
    @Transactional
    public void subscribeToSubject(String userEmail, Long subjectId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(subjectId));

        user.subscribe(subject);
    }

    /**
     * Unsubscribes a user from a subject.
     *
     * @param userEmail the email of the user unsubscribing from the subject
     * @param subjectId the identifier of the subject
     * @throws UserNotFoundException if the user does not exist
     * @throws SubjectNotFoundException if the subject does not exist
     */
    @Transactional
    public void unsubscribeFromSubject(String userEmail, Long subjectId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(subjectId));

        user.unsubscribe(subject);
    }

    /**
     * Retrieves all subjects the specified user is subscribed to.
     *
     * @param userEmail the email of the user
     * @return the list of subjects the user is subscribed to
     * @throws UserNotFoundException if the user does not exist
     */
    public List<SubjectDto> getUserSubscribedSubjects(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));
        List<SubjectDto> subjectDtos = user.getSubscribedSubjects()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return subjectDtos;
    }

    private SubjectDto mapToDto(Subject subject) {
        return new SubjectDto(
                subject.getId(),
                subject.getName(),
                subject.getDescription()
        );
    }
}
