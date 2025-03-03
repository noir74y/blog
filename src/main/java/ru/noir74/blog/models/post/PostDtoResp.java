package ru.noir74.blog.models.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.noir74.blog.models.comment.Comment;
import ru.noir74.blog.models.tag.Tag;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDtoResp {
    private Integer id;
    private String title;
    private String message;
    private Integer likes;
    private List<Tag> tags;
    private List<Comment> comments;
}
