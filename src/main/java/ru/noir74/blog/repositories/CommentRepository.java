package ru.noir74.blog.repositories;

import ru.noir74.blog.models.comment.CommentEntity;
import ru.noir74.blog.models.item.ItemEntity;
import ru.noir74.blog.models.item.ItemEntityBrief;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<CommentEntity> findById(Integer id);

    List<CommentEntity> findAllByItemId(Integer itemId);

    CommentEntity save(CommentEntity commentEntity);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
