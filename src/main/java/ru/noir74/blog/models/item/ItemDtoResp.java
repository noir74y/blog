package ru.noir74.blog.models.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.noir74.blog.models.comment.Comment;
import ru.noir74.blog.models.tag.Tag;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoResp {
    private Integer id;
    private String title;
    private String message;
    private Integer likes;
    private List<Tag> tags;
    private List<Comment> comments;
}
