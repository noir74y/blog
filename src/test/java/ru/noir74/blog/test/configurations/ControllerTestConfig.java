package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.noir74.blog.configurations.WebConfig;
import ru.noir74.blog.test.generics.GenericTestConfig;

@Import(WebConfig.class)
@ComponentScan("ru.noir74.blog.services")
public class ControllerTestConfig extends GenericTestConfig {
}
