package ru.noir74.blog.services;

import ru.noir74.blog.models.tag.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();

    Tag findById(Integer id);

    Tag findByName(String name);

    List<Tag> findAllByItemId(Integer itemId);

    Tag save(Tag tag);

    List<Tag> save(List<Tag> tags);

    void attachTagsToItem(List<Integer> tagIdList, Integer itemId);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    boolean existsByName(String name);
}
