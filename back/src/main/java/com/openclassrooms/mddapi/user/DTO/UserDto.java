package com.openclassrooms.mddapi.user.DTO;

import com.openclassrooms.mddapi.subject.SubjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String email;
    private List<SubjectDto> subscriptions;
}
