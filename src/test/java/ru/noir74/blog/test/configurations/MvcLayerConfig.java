package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DaoLayerConfig.class, ServiceLayerConfig.class})
@ComponentScan(basePackages = {
        "ru.noir74.blog.controllers",
        "ru.noir74.blog.configurations.web"
})
public class MvcLayerConfig {
}
