package ru.noir74.blog.repositories;

import ru.noir74.blog.models.tag.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    List<TagEntity> findAll();

    Optional<TagEntity> findById(Integer id);

    Optional<TagEntity> findByName(String name);

    List<TagEntity> findAllByItemId(Integer itemId);

    TagEntity save(TagEntity tagEntity);

    List<TagEntity> save(List<TagEntity> tagEntities);

    void unstickFromItem(Integer itemId);

    void stickToItem(List<Integer> tagIdList, Integer itemId);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    boolean existsByName(String name);
}
