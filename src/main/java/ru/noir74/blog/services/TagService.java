package ru.noir74.blog.services;

import ru.noir74.blog.models.tag.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();

    Tag findById(Integer id);

    List<Tag> findAllByItemId(Integer itemId);

    List<Tag> save(List<Tag> tags);

    void attachTagsToItem(List<Integer> tagIdList, Integer itemId);
}
