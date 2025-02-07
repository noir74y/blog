package ru.noir74.blog.services;

import ru.noir74.blog.models.comment.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAllByItemId(Integer itemId);

    Comment findById(Integer id);

    Comment create(Comment item);

    void update(Comment item);

    void delete(Integer id);
}
