package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.ComponentScan;
import ru.noir74.blog.test.generics.GenericTestConfig;

@ComponentScan(basePackages = {
        "ru.noir74.blog.configurations.dao",
        "ru.noir74.blog.repositories"})
public class DaoTestConfig extends GenericTestConfig {
}
