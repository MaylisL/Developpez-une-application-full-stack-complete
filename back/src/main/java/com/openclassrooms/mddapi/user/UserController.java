package com.openclassrooms.mddapi.user;

import com.openclassrooms.mddapi.auth.DTO.AuthResponse;
import com.openclassrooms.mddapi.user.DTO.UserUpdateDto;
import com.openclassrooms.mddapi.user.DTO.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for managing user profile operations.
 * <p>
 * Provides endpoints for retrieving and updating the profile of the
 * authenticated user.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Updates the profile information of the authenticated user.
     * <p>
     * The user is identified using the email extracted from the JWT token.
     *
     * @param userUpdateDto the data used to update the user profile
     * @param jwt the JWT token representing the authenticated user
     * @return the updated authentication response
     */
    @PutMapping("/update")
    public ResponseEntity<AuthResponse> updateUserProfile(@Valid @RequestBody UserUpdateDto userUpdateDto,
                                                          @AuthenticationPrincipal Jwt jwt) {
        String connectedUserEmail = jwt.getSubject();

        return ResponseEntity.ok(new AuthResponse(userService.updateUser(connectedUserEmail, userUpdateDto)));
    }

    /**
     * Retrieves the profile of the authenticated user.
     *
     * @param jwt the JWT token representing the authenticated user
     * @return the user profile information
     */
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserProfile(@AuthenticationPrincipal Jwt jwt) {
        String connectedUserEmail = jwt.getSubject();
        UserDto userDto = userService.getUserProfile(connectedUserEmail);

        return ResponseEntity.ok(userDto);

    }

}
