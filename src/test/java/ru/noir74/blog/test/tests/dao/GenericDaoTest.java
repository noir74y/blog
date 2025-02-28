package ru.noir74.blog.test.tests.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.noir74.blog.test.configurations.DaoTestConfig;
import ru.noir74.blog.test.tests.GenericTest;

@ExtendWith(SpringExtension.class)
@ContextHierarchy(@ContextConfiguration(name = "root", classes = DaoTestConfig.class))
public abstract class GenericDaoTest extends GenericTest {
}
