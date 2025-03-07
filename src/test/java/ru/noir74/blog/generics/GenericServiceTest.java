package ru.noir74.blog.generics;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.configurations.ServiceTestConfig;

@SpringJUnitConfig
@ContextHierarchy(@ContextConfiguration(name = "service", classes = ServiceTestConfig.class))
public abstract class GenericServiceTest extends GenericTest {
}
