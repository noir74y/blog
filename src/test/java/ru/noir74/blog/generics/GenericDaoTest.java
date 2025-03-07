package ru.noir74.blog.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import ru.noir74.blog.configurations.DaoTestConfig;
import ru.noir74.blog.repositories.intf.PostRepository;

@DataJpaTest
@ContextHierarchy(@ContextConfiguration(name = "dao", classes = DaoTestConfig.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class GenericDaoTest extends GenericTest {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected PostRepository postRepository;
}
