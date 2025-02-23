package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.noir74.blog.configurations.root.ModelMapperConfig;

@Configuration
@Import(ModelMapperConfig.class)
@ComponentScan("ru.noir74.blog.models")
public class ModelsTestConfig {
}
