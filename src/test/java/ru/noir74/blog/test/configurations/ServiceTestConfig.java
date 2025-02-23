package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MappersTestConfig.class,ModelsTestConfig.class})
public class ServiceTestConfig {
}
