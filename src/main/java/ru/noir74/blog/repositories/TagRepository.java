package ru.noir74.blog.repositories;

import ru.noir74.blog.models.tag.TagEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository {
    List<TagEntity> findAll();

    Optional<TagEntity> findById(Integer id);

    List<TagEntity> findAllByItemId(Integer itemId);

    TagEntity save(TagEntity tagEntity);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
