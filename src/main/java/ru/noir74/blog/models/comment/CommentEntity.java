package ru.noir74.blog.models.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    private Integer id;
    private String message;
    private Integer itemId;
    private Timestamp changed;
}
