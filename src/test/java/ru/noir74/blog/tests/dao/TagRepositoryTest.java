package ru.noir74.blog.tests.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.noir74.blog.generics.GenericDaoTest;
import ru.noir74.blog.models.tag.TagEntity;
import ru.noir74.blog.repositories.intf.TagRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagRepositoryTest extends GenericDaoTest {
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM tags");
        jdbcTemplate.execute("INSERT INTO tags (name) VALUES ('tag')");
        tagRepository.refreshAllTagList();
    }

    @Test
    void findAll() {
        assertEquals(1, tagRepository.findAll().size());
    }

    @Test
    void findById() {
        var newTag = tagRepository.save(TagEntity.builder().name("newTag").build());
        assertEquals(newTag, tagRepository.findById(newTag.getId()).orElse(TagEntity.builder().build()));
    }

    @Test
    void saveListOfTags() {
        var newTags = new ArrayList<>(List.of(TagEntity.builder().name("newTag").build()));
        var newTagId = tagRepository.save(newTags).getFirst().getId();
        assertEquals(newTags.getFirst().getName(), tagRepository.findById(newTagId).orElse(TagEntity.builder().build()).getName());
    }

    @Test
    void findAllByPostId() {
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("INSERT INTO posts (title,message) VALUES ('title','message')");
        var allPosts = postRepository.findByPage(1, 10);
        var allTags = tagRepository.findAll();
        var postId = allPosts.getFirst().getId();
        var tagId = allTags.getFirst().getId();
        var tagName = allTags.getFirst().getName();
        jdbcTemplate.update("INSERT INTO posts_tags (post_id, tag_id) VALUES (?, ?)", postId, tagId);

        var tags = tagRepository.findAllByPostId(postId);
        assertEquals(tags.getFirst(), TagEntity.builder().id(tagId).name(tagName).build());
    }

    @Test
    void unstickFromPost() {
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("INSERT INTO posts (title,message) VALUES ('title','message')");
        var allPosts = postRepository.findByPage(1, 10);
        var allTags = tagRepository.findAll();
        var postId = allPosts.getFirst().getId();
        var tagId = allTags.getFirst().getId();
        var tagName = allTags.getFirst().getName();
        jdbcTemplate.update("INSERT INTO posts_tags (post_id, tag_id) VALUES (?, ?)", postId, tagId);

        tagRepository.unstickFromPost(postId);
        assertEquals(0, tagRepository.findAllByPostId(postId).size());
    }

    @Test
    void stickToPost() {
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("INSERT INTO posts (title,message) VALUES ('title','message')");
        var allPosts = postRepository.findByPage(1, 10);
        var allTags = tagRepository.findAll();
        var postId = allPosts.getFirst().getId();
        var tag = allTags.getFirst();
        var tagId = tag.getId();
        var tagName = tag.getName();

        tagRepository.stickToPost(List.of(tagId), postId);
        assertEquals(List.of(tag), tagRepository.findAllByPostId(postId));
    }
}
