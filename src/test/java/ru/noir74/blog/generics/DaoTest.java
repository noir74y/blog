package ru.noir74.blog.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import ru.noir74.blog.configurations.ModelMapperConfig;
import ru.noir74.blog.repositories.impl.CommentRepositoryImpl;
import ru.noir74.blog.repositories.impl.PostRepositoryImpl;
import ru.noir74.blog.repositories.impl.TagRepositoryImpl;
import ru.noir74.blog.repositories.intf.PostRepository;

@DataJpaTest
@ContextConfiguration(classes = {
        ModelMapperConfig.class,
        TagRepositoryImpl.class,
        CommentRepositoryImpl.class,
        PostRepositoryImpl.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class DaoTest {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected PostRepository postRepository;
}