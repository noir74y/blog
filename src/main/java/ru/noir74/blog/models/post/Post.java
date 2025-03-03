package ru.noir74.blog.models.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.noir74.blog.models.comment.Comment;
import ru.noir74.blog.models.tag.Tag;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Integer id;
    private String title;
    private String message;
    private Integer likes;
    private List<Comment> comments;
    private List<Tag> tags;
    private MultipartFile file;
    private Timestamp changed;
}
