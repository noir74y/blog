package ru.noir74.blog.test.tests.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.test.configurations.old.DaoLayerConfig;

@SpringJUnitConfig(classes = DaoLayerConfig.class)
@TestPropertySource(locations = "classpath:test-application.properties")
@ExtendWith(MockitoExtension.class)
public abstract class GenericDaoTest {
}
