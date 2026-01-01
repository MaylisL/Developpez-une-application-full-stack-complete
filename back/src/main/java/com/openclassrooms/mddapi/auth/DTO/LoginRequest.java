package com.openclassrooms.mddapi.auth.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String emailOrUsername;
    private String password;
}