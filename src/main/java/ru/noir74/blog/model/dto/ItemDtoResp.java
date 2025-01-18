package ru.noir74.blog.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.noir74.blog.model.domain.Comment;
import ru.noir74.blog.model.domain.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static ru.noir74.blog.ApplicationConfig.DATE_TIME_FORMAT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoResp {
    private Integer id;
    private String title;
    private String message;
    //private byte[] picture;
    private Integer likes;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime created;
    private Set<Tag> tags;
    private List<Comment> comments;
    private Integer commentsCounter;
}
