package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MappersTestConfig.class)
public class ServiceTestConfig {
}
