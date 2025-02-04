package ru.noir74.blog.services;

import ru.noir74.blog.models.tag.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> getAll();

    Tag findById(Integer id);

    List<Tag> findAllByItemId(Integer itemId);

    void save(Tag tag);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    boolean existsByName(String name);
}
