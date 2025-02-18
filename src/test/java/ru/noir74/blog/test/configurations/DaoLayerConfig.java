package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ModelLayerConfig.class)
@ComponentScan(basePackages = {
        "ru.noir74.blog.repositories",
        "ru.noir74.blog.test.configurations.dao"
})
public class DaoLayerConfig {
}
