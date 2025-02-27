package ru.noir74.blog.test.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import ru.noir74.blog.configurations.db.DataSourceConfig;

import javax.sql.DataSource;

@Configuration
@Import(DataSourceConfig.class)
@DependsOn("DataSourceConfig")
public class DbContentConfig {
    public ResourceDatabasePopulator resourceDatabasePopulator(DataSource dataSource) {
        ResourceDatabasePopulator loader = new ResourceDatabasePopulator();
        loader.addScript(new ClassPathResource("test-schema.sql"));
        loader.execute(dataSource);
        return loader;
    }
}
