package ru.noir74.blog.test.services;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.test.TestConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = TestConfig.class)
@TestPropertySource(locations = "classpath:test-application.properties")
public class TagServiceTest {
    @Test
    void myTest() {
        assertEquals(5, 2 + 3);
    }
}
