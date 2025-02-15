package ru.noir74.blog.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration

@ComponentScan(basePackages = {
        "ru.noir74.blog.configurations.web",
        "ru.noir74.blog.controllers"
})

public class WebConfig {
}
