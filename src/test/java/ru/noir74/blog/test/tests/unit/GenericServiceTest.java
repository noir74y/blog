package ru.noir74.blog.test.tests.unit;

import org.junit.jupiter.api.extension.ExtendWith;
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
import ru.noir74.blog.services.*;
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

    @Autowired
    protected TagRepository tagRepositoryMock;
    @Autowired
    protected CommentRepository commentRepositoryMock;
    @Autowired
    protected ItemRepository itemRepositoryMock;

    @Autowired
    protected TagService tagService;
    @Autowired
    protected CommentService commentService;
    @Autowired
    protected ItemService itemService;

//    @Autowired
//    protected TagService tagServiceMock;
//    @Mock
//    protected CommentService commentServiceMock;




//
//    @BeforeEach
//    void genericSetUp() {
//        tagService = new TagServiceImpl(tagRepositoryMock, tagMapper);
//        commentService = new CommentServiceImpl(commentRepositoryMock, commentMapper);
//        itemService = new ItemServiceImpl(itemRepositoryMock, itemMapper, itemImageMapper, tagServiceMock, commentServiceMock);
//    }
}
