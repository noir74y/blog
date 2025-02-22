package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.configurations.root.MapperConfig;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.ItemImageMapper;
import ru.noir74.blog.models.tag.TagEntity;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.repositories.CommentRepository;
import ru.noir74.blog.repositories.TagRepository;
import ru.noir74.blog.services.*;

import java.util.LinkedList;

@SpringJUnitConfig({MapperConfig.class, CommentMapper.class, TagMapper.class, ItemImageMapper.class})
public class ItemServiceTest extends GenericServiceTest {
    @Autowired
    private CommentMapper commentMapper;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private LinkedList<TagEntity> tags;

    @Autowired
    private TagMapper tagMapper;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagService tagService;

    @Autowired
    private ItemImageMapper itemImageMapper;

//    @Autowired
//    private ItemMapper itemMapper;


//    @Autowired
//    private ItemMapper itemMapper;

//    @Autowired
//    private ItemImageMapper itemImageMapper;
//
//    @Autowired
//    private TagService tagService;
//
//
//    @Mock
//    private CommentRepository commentRepository;
//
//    @InjectMocks
//    private CommentService commentService;
//
//    @Mock
//    private ItemRepository itemRepository;
//
//    @InjectMocks
//    private ItemService itemService;

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
