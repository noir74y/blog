package ru.noir74.blog.model.dto;

import ru.noir74.blog.model.domain.Comment;
import ru.noir74.blog.model.domain.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ItemDtoResp {
    private Integer id;
    private String title;
    private String message;
    private byte[] picture;
    private Integer likes;
    private LocalDateTime created;
    private Set<Tag> tags;
    private List<Comment> comments;
}
