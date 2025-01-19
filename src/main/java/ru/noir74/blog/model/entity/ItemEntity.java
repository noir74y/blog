package ru.noir74.blog.model.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    private Integer id;
    private String title;
    private String message;
    private Integer likes;
    private LocalDateTime created;
    private Set<CommentEntity> comments;
    private Set<TagEntity> tags;
}
