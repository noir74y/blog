package ru.noir74.blog.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Integer id;
    private String title;
    private String post;
    private byte[] picture;
    private Integer likes;
    private LocalDateTime created;
    private HashSet<Tag> tags;
    private LinkedHashSet<Comment> comments;
}
