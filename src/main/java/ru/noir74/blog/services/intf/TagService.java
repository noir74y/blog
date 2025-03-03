package ru.noir74.blog.services.intf;

import ru.noir74.blog.models.tag.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();

    Tag findById(Integer id);

    List<Tag> findAllByPostId(Integer postId);

    List<Tag> save(List<Tag> tags);

    void attachTagsToPost(List<Integer> tagIdList, Integer postId);

    void populateTags();
}
