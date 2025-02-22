package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import ru.noir74.blog.mappers.ItemImageMapper;
import ru.noir74.blog.mappers.ItemMapper;
import ru.noir74.blog.repositories.ItemRepository;
import ru.noir74.blog.services.CommentService;
import ru.noir74.blog.services.ItemServiceImpl;
import ru.noir74.blog.services.TagService;

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
