package ru.noir74.blog.test.tests.unit;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.repositories.TagRepository;
import ru.noir74.blog.services.TagService;
import ru.noir74.blog.services.TagServiceImpl;
import ru.noir74.blog.test.configurations.ServiceTestConfig;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
public class GenericServiceTest {
    @Autowired
    protected TagMapper tagMapper;
    @Autowired
    protected TagRepository tagRepositoryMock;
    @Autowired
    protected TagService tagService;

//    @Autowired
//    protected CommentMapper commentMapper;
//    @Autowired
//    protected ItemImageMapper itemImageMapper;
//    @Autowired
//    protected ItemMapper itemMapper;
//

//    @Mock
//    protected CommentRepository commentRepositoryMock;
//    @Mock
//    protected ItemRepository itemRepositoryMock;

//    @Autowired
//    protected TagService tagServiceMock;
//    @Mock
//    protected CommentService commentServiceMock;



//    protected CommentServiceImpl commentService;
//    protected ItemServiceImpl itemService;
//
//    @BeforeEach
//    void genericSetUp() {
//        tagService = new TagServiceImpl(tagRepositoryMock, tagMapper);
//        commentService = new CommentServiceImpl(commentRepositoryMock, commentMapper);
//        itemService = new ItemServiceImpl(itemRepositoryMock, itemMapper, itemImageMapper, tagServiceMock, commentServiceMock);
//    }
}
