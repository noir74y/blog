package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "ru.noir74.blog.configurations.db",
        "ru.noir74.blog.repositories"})
public class DaoTestConfig {
}
