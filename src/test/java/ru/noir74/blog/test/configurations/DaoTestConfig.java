package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {
        "ru.noir74.blog.repositories",
        "ru.noir74.blog.test.configurations.db"})
public class DaoTestConfig {
    public ResourceDatabasePopulator resourceDatabasePopulator(DataSource dataSource) {
        ResourceDatabasePopulator loader = new ResourceDatabasePopulator();
        loader.addScript(new ClassPathResource("test-schema.sql"));
        loader.execute(dataSource);
        return loader;
    }
}
