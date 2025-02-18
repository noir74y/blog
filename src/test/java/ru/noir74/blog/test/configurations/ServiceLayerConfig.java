package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ModelLayerConfig.class)
@ComponentScan("ru.noir74.blog.services")
public class ServiceLayerConfig {
}
