package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.ItemImageMapper;
import ru.noir74.blog.mappers.ItemMapper;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.repositories.CommentRepository;
import ru.noir74.blog.repositories.ItemRepository;
import ru.noir74.blog.repositories.TagRepository;
import ru.noir74.blog.services.CommentServiceImpl;
import ru.noir74.blog.services.ItemServiceImpl;
import ru.noir74.blog.services.TagServiceImpl;
import ru.noir74.blog.test.configurations.ServiceTestConfig;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
public class GenericServiceTest {
    @Autowired
    protected TagMapper tagMapper;
    @Autowired
    protected CommentMapper commentMapper;
    @Autowired
    protected ItemImageMapper itemImageMapper;
    @Autowired
    protected ItemMapper itemMapper;

    @Mock
    protected TagRepository tagRepository;
    @Mock
    protected CommentRepository commentRepository;
    @Mock
    protected ItemRepository itemRepository;

    protected TagServiceImpl tagService;
    protected CommentServiceImpl commentService;
    protected ItemServiceImpl itemService;

    @BeforeEach
    void genericSetUp() {
        tagService = new TagServiceImpl(tagRepository, tagMapper);
        commentService = new CommentServiceImpl(commentRepository, commentMapper);
        itemService = new ItemServiceImpl(itemRepository, itemMapper, itemImageMapper, tagService, commentService);
    }
}
