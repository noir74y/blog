package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.configurations.root.MapperConfig;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.ItemImageMapper;
import ru.noir74.blog.mappers.ItemMapper;
import ru.noir74.blog.mappers.TagMapper;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig({MapperConfig.class, CommentMapper.class, TagMapper.class, ItemImageMapper.class, ItemMapper.class})
public abstract class GenericServiceTest {
}
