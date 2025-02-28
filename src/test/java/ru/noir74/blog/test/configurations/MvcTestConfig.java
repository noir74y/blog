package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.noir74.blog.configurations.RootConfig;
import ru.noir74.blog.configurations.WebConfig;

@Configuration
@Import({WebConfig.class, RootConfig.class})
@ComponentScan("ru.noir74.blog.mappers")
public class MvcTestConfig {
}
