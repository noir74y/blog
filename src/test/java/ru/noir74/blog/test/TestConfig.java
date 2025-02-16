package ru.noir74.blog.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.noir74.blog.configurations.root.MapperConfig;

@Configuration
@Import(MapperConfig.class)
@ComponentScan(basePackages = {
        "ru.noir74.blog.test.configurations",
        "ru.noir74.blog.models",
        "ru.noir74.blog.repositories",
        "ru.noir74.blog.services"
})
@TestPropertySource(locations = "classpath:test-application.properties")
public class TestConfig {
}
