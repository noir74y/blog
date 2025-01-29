package ru.noir74.blog.repositories;

import ru.noir74.blog.models.tag.TagEntity;

import java.util.List;

public interface TagRepository {
    List<TagEntity> getAll();
}
