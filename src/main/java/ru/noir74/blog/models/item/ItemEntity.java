package ru.noir74.blog.models.item;

import lombok.*;
import ru.noir74.blog.models.comment.CommentEntity;
import ru.noir74.blog.models.tag.TagEntity;

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
