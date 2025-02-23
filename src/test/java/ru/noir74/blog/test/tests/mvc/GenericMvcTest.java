package ru.noir74.blog.test.tests.mvc;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.noir74.blog.test.configurations.old.DaoLayerConfig;

@SpringJUnitConfig(classes = DaoLayerConfig.class)
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
public abstract class GenericMvcTest {
}
