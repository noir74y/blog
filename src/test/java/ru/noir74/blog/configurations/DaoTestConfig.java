package ru.noir74.blog.configurations;

import org.springframework.context.annotation.ComponentScan;
import ru.noir74.blog.generics.GenericTestConfig;

@ComponentScan(basePackages = {"ru.noir74.blog.repositories"})
public class DaoTestConfig extends GenericTestConfig {
}
