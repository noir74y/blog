package ru.noir74.blog.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import ru.noir74.blog.repositories.intf.PostRepository;

@DataJpaTest
@ContextConfiguration(classes = DaoTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class DaoTest {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected PostRepository postRepository;
}

@TestConfiguration
@ComponentScan(basePackages = {"ru.noir74.blog.repositories"})
class DaoTestConfig {
}
