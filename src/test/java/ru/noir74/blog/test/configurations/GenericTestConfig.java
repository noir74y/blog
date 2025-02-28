package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.noir74.blog.configurations.ModelMapperConfig;

@Configuration
@Import(ModelMapperConfig.class)
public abstract class GenericTestConfig {
}
