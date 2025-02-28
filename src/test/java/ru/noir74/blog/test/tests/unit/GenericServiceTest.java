package ru.noir74.blog.test.tests.unit;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.configurations.ModelMapperConfig;
import ru.noir74.blog.test.configurations.UnitConfig;
import ru.noir74.blog.test.tests.GenericTest;

@SpringJUnitConfig
@ContextHierarchy({
        @ContextConfiguration(name = "mapper", classes = ModelMapperConfig.class),
        @ContextConfiguration(name = "unit", classes = UnitConfig.class)
})
public abstract class GenericServiceTest extends GenericTest {
}
