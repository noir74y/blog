package ru.noir74.blog.test.tests.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.noir74.blog.models.comment.CommentEntity;
import ru.noir74.blog.models.item.ItemEntityBrief;
import ru.noir74.blog.repositories.intf.CommentRepository;
import ru.noir74.blog.repositories.intf.ItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CommentRepositoryTest extends GenericDaoTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ItemRepository itemRepository;

    List<ItemEntityBrief> allItems;
    Integer itemId;
    List<CommentEntity> comments;
    Integer commentId;
    String message;


    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM items");
        jdbcTemplate.execute("INSERT INTO items (title,message) VALUES ('title','message')");
        allItems = itemRepository.findByPage(1, 10);
        itemId = allItems.getFirst().getId();
        jdbcTemplate.execute("DELETE FROM comments");
        message = "message";
        commentId = commentRepository.save(CommentEntity.builder().message(message).itemId(itemId).build());
    }

    @Test
    void findById() {
        var comment = commentRepository.findById(commentId);
        assertEquals(comment.orElse(null), CommentEntity.builder().id(commentId).message(message).itemId(itemId).build());
    }

    @Test
    void findAllByItemId() {
        assertEquals(
                List.of(CommentEntity.builder().id(commentId).message(message).itemId(itemId).build()),
                commentRepository.findAllByItemId(itemId));
    }

    @Test
    void update() {
        var messageUpdated = message + "Updated";
        var commentUpdated = CommentEntity.builder().id(commentId).message(messageUpdated).itemId(itemId).build();
        commentRepository.save(commentUpdated);
        assertEquals(
                commentUpdated,
                commentRepository.findById(commentId).orElse(null));
    }

    @Test
    void deleteById() {
        commentRepository.deleteById(commentId);
        assertFalse(commentRepository.existsById(commentId));
    }


//    @Test
//    void saveListOfTags() {
//        var newTags = new ArrayList<>(List.of(TagEntity.builder().name("newTag").build()));
//        var newTagId = tagRepository.save(newTags).getFirst().getId();
//        assertEquals(newTags.getFirst().getName(), tagRepository.findById(newTagId).orElse(TagEntity.builder().build()).getName());
//    }
//
//    @Test
//    void findAllByItemId() {
//        jdbcTemplate.execute("DELETE FROM items");
//        jdbcTemplate.execute("INSERT INTO items (title,message) VALUES ('title','message')");
//        var allItems = itemRepository.findByPage(1, 10);
//        var allTags = tagRepository.findAll();
//        var itemId = allItems.getFirst().getId();
//        var tagId = allTags.getFirst().getId();
//        var tagName = allTags.getFirst().getName();
//        jdbcTemplate.update("INSERT INTO items_tags (item_id, tag_id) VALUES (?, ?)", itemId, tagId);
//
//        var tags = tagRepository.findAllByItemId(itemId);
//        assertEquals(tags.getFirst(), TagEntity.builder().id(tagId).name(tagName).build());
//    }
//
//    @Test
//    void unstickFromItem() {
//        jdbcTemplate.execute("DELETE FROM items");
//        jdbcTemplate.execute("INSERT INTO items (title,message) VALUES ('title','message')");
//        var allItems = itemRepository.findByPage(1, 10);
//        var allTags = tagRepository.findAll();
//        var itemId = allItems.getFirst().getId();
//        var tagId = allTags.getFirst().getId();
//        var tagName = allTags.getFirst().getName();
//        jdbcTemplate.update("INSERT INTO items_tags (item_id, tag_id) VALUES (?, ?)", itemId, tagId);
//
//        tagRepository.unstickFromItem(itemId);
//        assertEquals(0, tagRepository.findAllByItemId(itemId).size());
//    }
//
//    @Test
//    void stickToItem() {
//        jdbcTemplate.execute("DELETE FROM items");
//        jdbcTemplate.execute("INSERT INTO items (title,message) VALUES ('title','message')");
//        var allItems = itemRepository.findByPage(1, 10);
//        var allTags = tagRepository.findAll();
//        var itemId = allItems.getFirst().getId();
//        var tag = allTags.getFirst();
//        var tagId = tag.getId();
//        var tagName = tag.getName();
//
//        tagRepository.stickToItem(List.of(tagId), itemId);
//        assertEquals(List.of(tag), tagRepository.findAllByItemId(itemId));
//    }
}
