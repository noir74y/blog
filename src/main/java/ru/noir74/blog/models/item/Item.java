package ru.noir74.blog.models.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.noir74.blog.models.comment.Comment;
import ru.noir74.blog.models.tag.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Integer id;
    private String title;
    private String message;
    private byte[] picture;
    private Integer likes;
    private LocalDateTime created;
    private List<Comment> comments;
    private List<Tag> tags;
}
