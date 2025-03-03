package ru.noir74.blog.repositories.intf;

import ru.noir74.blog.models.tag.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    List<TagEntity> findAll();

    Optional<TagEntity> findById(Integer id);

    List<TagEntity> findAllByPostId(Integer postId);

    TagEntity save(TagEntity tagEntity);

    List<TagEntity> save(List<TagEntity> tagEntities);

    void unstickFromPost(Integer postId);

    void stickToPost(List<Integer> tagIdList, Integer postId);

    void refreshAllTagList();
}
