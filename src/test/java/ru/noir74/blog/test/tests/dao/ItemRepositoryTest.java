package ru.noir74.blog.test.tests.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.noir74.blog.models.comment.CommentEntity;
import ru.noir74.blog.models.item.ItemEntity;
import ru.noir74.blog.models.item.ItemEntityBrief;
import ru.noir74.blog.models.item.ItemImageEntity;
import ru.noir74.blog.repositories.intf.CommentRepository;
import ru.noir74.blog.test.generics.GenericDaoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ItemRepositoryTest extends GenericDaoTest {
    @Autowired
    private CommentRepository commentRepository;

    List<ItemEntityBrief> allItems;
    Integer itemId;
    List<CommentEntity> comments;
    Integer commentId;
    String title;
    String message;

    @BeforeEach
    void setUp() {
        title = "title";
        message = "message";
        jdbcTemplate.execute("DELETE FROM items");
        jdbcTemplate.update("INSERT INTO items (title,message) VALUES (?,?)", title, message);
        allItems = itemRepository.findByPage(1, 10);
        itemId = allItems.getFirst().getId();
    }

    @Test
    void findById() {
        var item = itemRepository.findById(itemId);
        assertEquals(item.orElse(null), ItemEntity.builder().id(itemId).title(title).likes(0).message(message).build());
    }

    @Test
    void insert() {
        var newItemToCreate = ItemEntity.builder().title(title).message(message).likes(0).build();
        var newItemId = itemRepository.save(newItemToCreate);
        var newItemCreated = itemRepository.findById(newItemId).orElse(null);
        newItemToCreate.setId(newItemId);
        assertEquals(newItemToCreate, newItemCreated);
    }


    @Test
    void update() {
        var itemToUpdate = itemRepository.findById(itemId).orElse(ItemEntity.builder().build());
        var messageUpdated = itemToUpdate.getMessage() + "Updated";
        itemToUpdate.setMessage(messageUpdated);
        itemRepository.save(itemToUpdate);
        var itemUpdated = itemRepository.findById(itemId).orElse(ItemEntity.builder().build());
        assertEquals(messageUpdated, itemUpdated.getMessage());
    }


    @Test
    void deleteById() {
        itemRepository.deleteById(itemId);
        assertFalse(itemRepository.existsById(itemId));
    }

    @Test
    void testImage() {
        var image = new byte[]{(byte) 0x00};
        var itemImageEntityToSave = ItemImageEntity.builder().id(itemId).imageName("imageName").image(image).build();
        itemRepository.saveImageById(itemImageEntityToSave);
        var itemImageEntitySaved = itemRepository.findImageById(itemId);
        assertEquals(itemImageEntityToSave, itemImageEntitySaved);
    }
}
