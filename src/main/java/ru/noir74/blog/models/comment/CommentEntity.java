package ru.noir74.blog.models.comment;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    private Integer id;
    private String message;
    private LocalDateTime created;
}
