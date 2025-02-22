package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.configurations.root.ModelMapperConfig;
import ru.noir74.blog.test.configurations.MappersTestConfig;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig({ModelMapperConfig.class, MappersTestConfig.class})
public class GenericServiceTest {
}
