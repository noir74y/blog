package ru.noir74.blog.test.tests.mvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.noir74.blog.test.configurations.old.MvcTestConfig;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MvcTestConfig.class})
@TestPropertySource(locations = "classpath:test-application.properties")
public abstract class GenericMvcTest {
    @Test
    void test() {
    }
}
