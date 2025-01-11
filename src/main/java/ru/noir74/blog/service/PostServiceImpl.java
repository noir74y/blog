package ru.noir74.blog.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.model.domain.Post;
import ru.noir74.blog.model.entity.PostEntity;
import ru.noir74.blog.model.mapper.PostMapper;
import ru.noir74.blog.repository.CommentRepository;
import ru.noir74.blog.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    @Transactional(readOnly = true)
    public Post findById(Integer id) {
        return postMapper.entity2Model(postRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Post create(Post post) {
        return postMapper.entity2Model(postRepository.save(postMapper.model2entity(post)));
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
