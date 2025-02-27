package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.noir74.blog.configurations.RootConfig;
import ru.noir74.blog.configurations.WebConfig;

@Configuration
@EnableWebMvc
@Import({WebConfig.class, RootConfig.class})
@ComponentScan("ru.noir74.blog.mappers")
public class MvcTestConfig {
}
