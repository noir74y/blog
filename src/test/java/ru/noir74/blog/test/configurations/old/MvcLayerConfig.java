package ru.noir74.blog.test.configurations.old;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.noir74.blog.test.configurations.MappersTestConfig;

@Configuration
@Import({DaoLayerConfig.class, MappersTestConfig.class})
@ComponentScan(basePackages = {
        "ru.noir74.blog.controllers",
        "ru.noir74.blog.configurations.web"
})
public class MvcLayerConfig {
}
