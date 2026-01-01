package com.openclassrooms.mddapi.comment.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    String author;
    String content;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDateTime createdAt;
}
