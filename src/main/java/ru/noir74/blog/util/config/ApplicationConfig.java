package ru.noir74.blog.util.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Clock;

@Configuration
@ComponentScan(basePackages = "ru.noir74.spring.blog")
public class ApplicationConfig {
}
