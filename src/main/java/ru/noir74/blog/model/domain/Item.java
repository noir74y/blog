package ru.noir74.blog.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
