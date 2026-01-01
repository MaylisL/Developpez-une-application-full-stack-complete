package com.openclassrooms.mddapi.user;

import com.openclassrooms.mddapi.auth.service.AuthService;
import com.openclassrooms.mddapi.core.exception.UserNotFoundException;
import com.openclassrooms.mddapi.subject.Subject;
import com.openclassrooms.mddapi.subject.SubjectDto;
import com.openclassrooms.mddapi.user.DTO.UserDto;
import com.openclassrooms.mddapi.user.DTO.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service responsible for managing user-related operations.
 * <p>
 * Handles user profile retrieval, profile updates, authentication token
 * generation, and user-related mapping logic.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves a user entity by email.
     *
     * @param email the email of the user
     * @return the user entity, or {@code null} if no user is found
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * Retrieves the profile information of a user.
     *
     * @param email the email of the user
     * @return the user's profile data
     * @throws UserNotFoundException if no user exists with the given email
     */
    public UserDto getUserProfile(String email) {
        User user = getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        return mapUserToUserDto(user);

    }

    /**
     * Updates the profile information of a user.
     * <p>
     * Only non-null and non-blank fields from the update DTO are applied.
     * If the password is updated, it is encoded before being persisted.
     *
     * @param currentEmail the current email of the user
     * @param userUpdateDto the data used to update the user profile
     * @return a new authentication token for the updated user
     * @throws UserNotFoundException if the user does not exist
     */
    public String updateUser(String currentEmail, UserUpdateDto userUpdateDto) {
        User userToUpdate = getUserByEmail(currentEmail);
        if (userToUpdate == null) {
            throw new UserNotFoundException(currentEmail);
        }
        User updatedUser = userRepository.save(this.mapUserUpdateDtoToUser(userUpdateDto, userToUpdate));

        return authService.getTokenForUser(updatedUser);
    }

    /**
     * Retrieves the username of a user by their identifier.
     *
     * @param userId the identifier of the user
     * @return the username of the user
     * @throws java.util.NoSuchElementException if the user does not exist
     */
    public String getUserName(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getUsername();
    }

    private User mapUserUpdateDtoToUser(UserUpdateDto userUpdateDto, User user) {
        if(userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().isBlank()) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if(userUpdateDto.getUsername() != null && !userUpdateDto.getUsername().isBlank()) {
            user.setUsername(userUpdateDto.getUsername());
        }
        if(userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }

        return user;
    }

    private UserDto mapUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());

        List<SubjectDto> subjects = user.getSubscribedSubjects()
                .stream()
                .map(this::mapToSubjectDto)
                .toList();
        userDto.setSubscriptions(subjects);
        return userDto;
    }
    private SubjectDto mapToSubjectDto(Subject subject) {
        return new SubjectDto(
                subject.getId(),
                subject.getName(),
                subject.getDescription()
        );
    }

}
