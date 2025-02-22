package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.configurations.root.MapperConfig;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.ItemImageMapper;
import ru.noir74.blog.mappers.ItemMapper;
import ru.noir74.blog.models.tag.TagEntity;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.repositories.CommentRepository;
import ru.noir74.blog.repositories.ItemRepository;
import ru.noir74.blog.repositories.TagRepository;
import ru.noir74.blog.services.*;

import java.util.LinkedList;

public class ItemServiceTest extends GenericServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @Autowired
    private ItemImageMapper itemImageMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Mock
    private TagService tagService;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void testFindPage() {
    }

    @Test
    void testFindById() {
    }

    @Test
    void testFindImageById() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testSetImageById() {
    }

    @Test
    void testDelete() {
    }

}
