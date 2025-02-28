package ru.noir74.blog.test.tests.unit;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.test.configurations.UnitConfig;
import ru.noir74.blog.test.tests.GenericTest;

@SpringJUnitConfig
@ContextHierarchy({
        @ContextConfiguration(name = "unit", classes = UnitConfig.class)
})
public abstract class GenericServiceTest extends GenericTest {
}
