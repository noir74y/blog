package ru.noir74.blog.test.configurations.old;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.noir74.blog.test.configurations.ModelsTestConfig;

@Configuration
@Import(ModelsTestConfig.class)
@ComponentScan(basePackages = {
        "ru.noir74.blog.repositories",
        "ru.noir74.blog.test.configurations.dao"
})
public class DaoLayerConfig {
}
