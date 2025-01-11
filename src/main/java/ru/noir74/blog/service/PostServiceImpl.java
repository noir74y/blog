package ru.noir74.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.noir74.blog.model.domain.Post;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    @Override
    public Post findById(Integer id) {
        return null;
    }

    @Override
    public Post create(Post item) {
        return null;
    }

    @Override
    public void update(Post item) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void addLike(Integer id) {

    }

    @Override
    public void removeLike(Integer id) {

    }
}
