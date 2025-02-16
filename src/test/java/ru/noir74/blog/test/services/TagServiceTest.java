package ru.noir74.blog.test.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.noir74.blog.configurations.RootConfig;
import ru.noir74.blog.test.TestConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class TagServiceTest {
    @Test
    void myTest() {
        assertEquals(5, 2+3);
    }
}
