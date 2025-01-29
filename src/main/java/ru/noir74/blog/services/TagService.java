package ru.noir74.blog.services;

import ru.noir74.blog.models.tag.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAll();
}
