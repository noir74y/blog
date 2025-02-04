package ru.noir74.blog.models.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.noir74.blog.models.comment.CommentEntity;
import ru.noir74.blog.models.tag.TagEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    private Integer id;
    private String title;
    private String message;
    private byte[] picture;
    private Integer likes;
    private LocalDateTime created;
}
