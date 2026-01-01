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
public class PostDetailDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String subject;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdAt;
    //Set<CommentDto> comments;
}
