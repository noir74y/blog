package ru.noir74.blog.test.tests.services;

import net.bytebuddy.utility.nullability.AlwaysNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.configurations.root.ModelMapperConfig;
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
import ru.noir74.blog.test.configurations.MappersTestConfig;
import ru.noir74.blog.test.configurations.ServiceTestConfig;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
public class GenericServiceTest {
    protected CommentMapper commentMapper;

    @Mock
    protected CommentRepository commentRepository;

    @InjectMocks
    protected CommentServiceImpl commentService;

    @Autowired
    protected TagMapper tagMapper;

    @Mock
    protected TagRepository tagRepository;

    @Spy
    protected TagServiceImpl tagService;

    @Mock
    protected ItemRepository itemRepository;

    @Autowired
    protected ItemImageMapper itemImageMapper;

    @Autowired
    protected ItemMapper itemMapper;

    @InjectMocks
    protected ItemServiceImpl itemService;
}
