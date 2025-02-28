package ru.noir74.blog.test.tests.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.configurations.ModelMapperConfig;
import ru.noir74.blog.test.configurations.DaoTestConfig;
import ru.noir74.blog.test.tests.GenericTest;

@SpringJUnitConfig
@ContextHierarchy({
        @ContextConfiguration(name = "mapper", classes = ModelMapperConfig.class),
        @ContextConfiguration(name = "dao", classes = DaoTestConfig.class)
})
public abstract class GenericDaoTest extends GenericTest {
}
