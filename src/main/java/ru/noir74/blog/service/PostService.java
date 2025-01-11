package ru.noir74.blog.service;

import ru.noir74.blog.model.domain.Post;

import java.util.List;

public interface PostService {
    Post findById(Integer id);

    Post create(Post item);

    void update(Post item);

    void delete(Integer id);

    void addLike(Integer id);

    void removeLike(Integer id);
}
