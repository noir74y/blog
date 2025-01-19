package ru.noir74.blog.model.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    private Integer id;
    private String title;
    private String message;
    private byte[] picture;
    private Integer likes;
    private LocalDateTime created;
    private Set<CommentEntity> comments;
    private Set<TagEntity> tags;
}
