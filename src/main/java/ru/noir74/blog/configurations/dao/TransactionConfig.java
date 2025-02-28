package ru.noir74.blog.configurations.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class TransactionConfig {
    private final DataSource dataSource;

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
