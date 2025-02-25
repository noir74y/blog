package ru.noir74.blog.test.tests.dao;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.test.configurations.DaoTestConfig;

@SpringJUnitConfig(classes = DaoTestConfig.class)
@TestPropertySource(locations = "classpath:test-application.properties")
public abstract class GenericDaoTest {
}
