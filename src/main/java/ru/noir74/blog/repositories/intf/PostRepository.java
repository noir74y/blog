package ru.noir74.blog.repositories.intf;

import ru.noir74.blog.models.post.PostEntity;
import ru.noir74.blog.models.post.PostEntityBrief;
import ru.noir74.blog.models.post.PostImageEntity;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    List<PostEntityBrief> findByPage(Integer page, Integer size);

    Optional<PostEntity> findById(Integer id);

    PostImageEntity findImageById(Integer id);

    Integer save(PostEntity postEntity);

    void saveImageById(PostImageEntity postImageEntity);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    void addLike(Integer id);
}
