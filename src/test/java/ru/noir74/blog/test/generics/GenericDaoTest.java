package ru.noir74.blog.test.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import ru.noir74.blog.repositories.intf.PostRepository;
import ru.noir74.blog.test.configurations.DaoTestConfig;

@ContextHierarchy(@ContextConfiguration(name = "dao", classes = DaoTestConfig.class))
public abstract class GenericDaoTest extends GenericTest {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected PostRepository postRepository;
}
