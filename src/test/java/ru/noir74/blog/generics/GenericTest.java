package ru.noir74.blog.generics;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import ru.noir74.blog.configurations.ModelMapperConfig;

@ContextHierarchy(@ContextConfiguration(name = "mapper", classes = ModelMapperConfig.class))
public abstract class GenericTest {
}
