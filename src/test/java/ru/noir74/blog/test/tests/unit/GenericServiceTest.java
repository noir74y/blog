package ru.noir74.blog.test.tests.unit;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import ru.noir74.blog.test.configurations.UnitConfig;
import ru.noir74.blog.test.tests.GenericTest;

@ContextHierarchy(@ContextConfiguration(name = "unit", classes = UnitConfig.class))
public abstract class GenericServiceTest extends GenericTest {
}
