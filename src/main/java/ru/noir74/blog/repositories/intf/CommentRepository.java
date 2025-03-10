package ru.noir74.blog.repositories.intf;

import ru.noir74.blog.models.comment.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<CommentEntity> findById(Integer id);

    List<CommentEntity> findAllByPostId(Integer postId);

    Integer save(CommentEntity commentEntity);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
