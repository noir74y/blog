package ru.noir74.blog.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("ru.noir74.blog.services")
@Import(DaoConfig.class)
public class ServiceConfig {
}
