package ru.noir74.blog.services;

import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> getAll();

    Optional<Tag> findById(Integer id);

    List<Tag> findAllByItemId(Integer itemId);

    Tag save(Tag tag);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
