package ru.noir74.blog.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
        "ru.noir74.blog.configurations.web",
        "ru.noir74.blog.controllers"})
public class WebConfig {
}
