package ru.noir74.blog.services.intf;

import ru.noir74.blog.models.comment.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAllByPostId(Integer postId);

    Comment findById(Integer id);

    Integer create(Comment post);

    void update(Comment post);

    void delete(Integer id);
}
