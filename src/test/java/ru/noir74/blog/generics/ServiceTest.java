package ru.noir74.blog.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.noir74.blog.configurations.ModelMapperConfig;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.PostImageMapper;
import ru.noir74.blog.mappers.PostMapper;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.repositories.intf.CommentRepository;
import ru.noir74.blog.repositories.intf.PostRepository;
import ru.noir74.blog.repositories.intf.TagRepository;
import ru.noir74.blog.services.impl.CommentServiceImpl;
import ru.noir74.blog.services.impl.PostServiceImpl;
import ru.noir74.blog.services.impl.TagServiceImpl;
import ru.noir74.blog.services.intf.CommentService;
import ru.noir74.blog.services.intf.PostService;
import ru.noir74.blog.services.intf.TagService;

@SpringBootTest
@ContextConfiguration(classes = {
        ModelMapperConfig.class,
        TagServiceImpl.class,
        CommentServiceImpl.class,
        PostServiceImpl.class
})
public abstract class ServiceTest {
    @Autowired
    protected CommentMapper commentMapper;
    @Autowired
    protected PostMapper postMapper;
    @Autowired
    protected PostImageMapper postImageMapper;
    @Autowired
    protected TagMapper tagMapper;

    @MockBean
    protected TagRepository tagRepository;
    @MockBean
    protected CommentRepository commentRepository;
    @MockBean
    protected PostRepository postRepository;

    @Autowired
    protected TagService tagService;
    @Autowired
    protected CommentService commentService;
    @Autowired
    protected PostService postService;
}

