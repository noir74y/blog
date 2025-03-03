package ru.noir74.blog.test.tests.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.noir74.blog.services.intf.CommentService;
import ru.noir74.blog.services.intf.ItemService;
import ru.noir74.blog.test.configurations.ControllerTestConfig;
import ru.noir74.blog.test.generics.GenericDaoTest;

@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(name = "web", classes = ControllerTestConfig.class))
public abstract class GenericControllerTest extends GenericDaoTest {
    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected ItemService itemService;
    @Autowired
    protected CommentService commentService;
    protected MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        jdbcTemplate.execute("DELETE FROM items");
    }
}
