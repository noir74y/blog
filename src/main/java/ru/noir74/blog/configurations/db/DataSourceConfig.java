package ru.noir74.blog.configurations.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(
            @Value("${spring.datasource.url}") String dbUrl,
            @Value("${spring.datasource.username}") String dbUser,
            @Value("${spring.datasource.password}") String dbPass,
            @Value("${spring.datasource.driver-class-name}") String dbDriverClassName,
            @Value("${spring.datasource.init-sql-script}") String initSqlScript
    ) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPass);

        ResourceDatabasePopulator loader = new ResourceDatabasePopulator();
        loader.addScript(new ClassPathResource(initSqlScript));
        loader.execute(dataSource);

        return dataSource;
    }
}