package ru.noir74.blog.test.tests.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.noir74.blog.configurations.ModelMapperConfig;
import ru.noir74.blog.test.configurations.ServiceTestConfig;
import ru.noir74.blog.test.tests.GenericTest;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({
        @ContextConfiguration(name = "mapper", classes = ModelMapperConfig.class),
        @ContextConfiguration(name = "service", classes = ServiceTestConfig.class)
})
public abstract class GenericServiceTest extends GenericTest {
}
