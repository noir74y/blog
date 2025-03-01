package ru.noir74.blog.test.tests.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.noir74.blog.models.comment.CommentEntity;
import ru.noir74.blog.models.item.ItemEntityBrief;
import ru.noir74.blog.repositories.intf.CommentRepository;
import ru.noir74.blog.repositories.intf.ItemRepository;
import ru.noir74.blog.test.generics.GenericDaoTest;

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
        message = "message";

        jdbcTemplate.execute("DELETE FROM items");
        jdbcTemplate.update("INSERT INTO items (title,message) VALUES ('title',?)", message);
        allItems = itemRepository.findByPage(1, 10);
        itemId = allItems.getFirst().getId();
        jdbcTemplate.execute("DELETE FROM comments");
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
}
