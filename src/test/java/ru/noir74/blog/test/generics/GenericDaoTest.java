package ru.noir74.blog.test.generics;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import ru.noir74.blog.test.configurations.DaoTestConfig;

@ContextHierarchy(@ContextConfiguration(name = "dao", classes = DaoTestConfig.class))
public abstract class GenericDaoTest extends GenericTest {
}
