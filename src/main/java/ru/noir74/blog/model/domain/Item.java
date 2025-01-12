package ru.noir74.blog.model.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Integer id;
    private String title;
    private String post;
    private byte[] picture;
    private Integer likes;
    private LocalDateTime created;
    private Set<Tag> tags;
    private List<Comment> comments;
}
