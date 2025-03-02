package ru.noir74.blog.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration

@ComponentScan(basePackages = {
        "ru.noir74.blog.configurations.dao",
        "ru.noir74.blog.repositories"})
@Import(ModelMapperConfig.class)
@PropertySource("classpath:application.properties")
public class DaoConfig {
}
