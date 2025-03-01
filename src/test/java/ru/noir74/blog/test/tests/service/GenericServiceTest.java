package ru.noir74.blog.test.tests.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import ru.noir74.blog.test.configurations.ServiceTestConfig;
import ru.noir74.blog.test.tests.GenericTest;

@ContextHierarchy(@ContextConfiguration(name = "unit", classes = ServiceTestConfig.class))
public abstract class GenericServiceTest extends GenericTest {
}
