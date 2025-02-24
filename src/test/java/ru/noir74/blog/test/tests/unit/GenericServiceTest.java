package ru.noir74.blog.test.tests.unit;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.test.configurations.ServiceTestConfig;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ServiceTestConfig.class)
public class GenericServiceTest {
}
