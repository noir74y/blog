package ru.noir74.blog.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration

@ComponentScan(basePackages = {
        "ru.noir74.blog.configurations.root",
        "ru.noir74.blog.mappers",
        "ru.noir74.blog.repositories",
        "ru.noir74.blog.services"
})

@PropertySource("classpath:application.properties")
public class RootConfig {
}
