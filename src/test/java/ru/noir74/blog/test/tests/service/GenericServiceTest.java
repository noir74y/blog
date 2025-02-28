package ru.noir74.blog.test.tests.service;

import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.test.configurations.ServiceTestConfig;
import ru.noir74.blog.test.tests.GenericTest;

@SpringJUnitConfig(ServiceTestConfig.class)
public abstract class GenericServiceTest extends GenericTest {
}
