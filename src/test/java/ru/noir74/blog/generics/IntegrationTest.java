package ru.noir74.blog.generics;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.PostMapper;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.repositories.intf.PostRepository;
import ru.noir74.blog.services.intf.CommentService;
import ru.noir74.blog.services.intf.PostService;
import ru.noir74.blog.services.intf.TagService;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class IntegrationTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected PostService postService;
    @Autowired
    protected PostMapper postMapper;
    @Autowired
    protected TagService tagService;
    @Autowired
    protected TagMapper tagMapper;
    @Autowired
    protected CommentService commentService;
    @Autowired
    protected CommentMapper commentMapper;
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM posts");
    }
}
