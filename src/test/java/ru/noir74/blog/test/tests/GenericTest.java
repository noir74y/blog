package ru.noir74.blog.test.tests;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.configurations.ModelMapperConfig;

@SpringJUnitConfig
@ContextHierarchy(@ContextConfiguration(name = "mapper", classes = ModelMapperConfig.class))
@TestPropertySource(locations = "classpath:test-application.properties")
public abstract class GenericTest {
}
