package com.openclassrooms.mddapi.post.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdAt;
}
