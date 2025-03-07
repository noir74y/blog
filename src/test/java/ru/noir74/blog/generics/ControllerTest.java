package ru.noir74.blog.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.noir74.blog.controllers.CommentController;
import ru.noir74.blog.controllers.ImageController;
import ru.noir74.blog.controllers.LikeController;
import ru.noir74.blog.controllers.PostController;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.PostMapper;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.services.intf.CommentService;
import ru.noir74.blog.services.intf.PostService;
import ru.noir74.blog.services.intf.TagService;

@WebMvcTest(controllers = {CommentController.class, ImageController.class, LikeController.class, PostController.class})
public abstract class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected PostService postService;
    @MockBean
    protected PostMapper postMapper;
    @MockBean
    protected TagService tagService;
    @MockBean
    protected TagMapper tagMapper;
    @MockBean
    protected CommentService commentService;
    @MockBean
    protected CommentMapper commentMapper;
}
