package ru.noir74.blog.test.tests.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.noir74.blog.models.tag.TagEntity;
import ru.noir74.blog.repositories.intf.ItemRepository;
import ru.noir74.blog.repositories.intf.TagRepository;
import ru.noir74.blog.test.generics.GenericDaoTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagRepositoryTest extends GenericDaoTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ItemRepository itemRepository;

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
    void findAllByItemId() {
        jdbcTemplate.execute("DELETE FROM items");
        jdbcTemplate.execute("INSERT INTO items (title,message) VALUES ('title','message')");
        var allItems = itemRepository.findByPage(1, 10);
        var allTags = tagRepository.findAll();
        var itemId = allItems.getFirst().getId();
        var tagId = allTags.getFirst().getId();
        var tagName = allTags.getFirst().getName();
        jdbcTemplate.update("INSERT INTO items_tags (item_id, tag_id) VALUES (?, ?)", itemId, tagId);

        var tags = tagRepository.findAllByItemId(itemId);
        assertEquals(tags.getFirst(), TagEntity.builder().id(tagId).name(tagName).build());
    }

    @Test
    void unstickFromItem() {
        jdbcTemplate.execute("DELETE FROM items");
        jdbcTemplate.execute("INSERT INTO items (title,message) VALUES ('title','message')");
        var allItems = itemRepository.findByPage(1, 10);
        var allTags = tagRepository.findAll();
        var itemId = allItems.getFirst().getId();
        var tagId = allTags.getFirst().getId();
        var tagName = allTags.getFirst().getName();
        jdbcTemplate.update("INSERT INTO items_tags (item_id, tag_id) VALUES (?, ?)", itemId, tagId);

        tagRepository.unstickFromItem(itemId);
        assertEquals(0, tagRepository.findAllByItemId(itemId).size());
    }

    @Test
    void stickToItem() {
        jdbcTemplate.execute("DELETE FROM items");
        jdbcTemplate.execute("INSERT INTO items (title,message) VALUES ('title','message')");
        var allItems = itemRepository.findByPage(1, 10);
        var allTags = tagRepository.findAll();
        var itemId = allItems.getFirst().getId();
        var tag = allTags.getFirst();
        var tagId = tag.getId();
        var tagName = tag.getName();

        tagRepository.stickToItem(List.of(tagId), itemId);
        assertEquals(List.of(tag), tagRepository.findAllByItemId(itemId));
    }
}
