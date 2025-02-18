package ru.noir74.blog.test.tests.mvc;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.test.configurations.DaoLayerConfig;

@SpringJUnitConfig(classes = DaoLayerConfig.class)
@ExtendWith(MockitoExtension.class)
public abstract class GenericMvcTest {
}
