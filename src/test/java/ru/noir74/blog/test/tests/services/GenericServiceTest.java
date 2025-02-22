package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.ItemImageMapper;
import ru.noir74.blog.mappers.ItemMapper;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.models.tag.TagEntity;
import ru.noir74.blog.repositories.CommentRepository;
import ru.noir74.blog.repositories.ItemRepository;
import ru.noir74.blog.repositories.TagRepository;
import ru.noir74.blog.services.CommentServiceImpl;
import ru.noir74.blog.services.ItemServiceImpl;
import ru.noir74.blog.services.TagServiceImpl;
import ru.noir74.blog.test.configurations.ServiceTestConfig;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
public class GenericServiceTest {
    @Autowired
    protected TagMapper tagMapper;

    @Mock
    protected TagRepository tagRepository;

    protected TagServiceImpl tagService;

    @Autowired
    protected CommentMapper commentMapper;

    @Mock
    protected CommentRepository commentRepository;

    @InjectMocks
    protected CommentServiceImpl commentService;

    @Mock
    protected ItemRepository itemRepository;

    @Autowired
    protected ItemImageMapper itemImageMapper;

    @Autowired
    protected ItemMapper itemMapper;

    @InjectMocks
    protected ItemServiceImpl itemService;

    @BeforeEach
    void genericSetUp(){
        tagService = new TagServiceImpl(tagRepository,tagMapper);
    }
}
