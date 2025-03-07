package ru.noir74.blog.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.noir74.blog.repositories.intf.PostRepository;

@DataJpaTest
@ComponentScan(basePackages = {"ru.noir74.blog.repositories"})
//@ContextHierarchy(@ContextConfiguration(name = "dao", classes = DaoTestConfig.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class DaoTest extends Test {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected PostRepository postRepository;
}


