package ru.noir74.blog.test.generics;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import ru.noir74.blog.test.configurations.ServiceTestConfig;

@ContextHierarchy(@ContextConfiguration(name = "service", classes = ServiceTestConfig.class))
public abstract class GenericServiceTest extends GenericTest {
}
