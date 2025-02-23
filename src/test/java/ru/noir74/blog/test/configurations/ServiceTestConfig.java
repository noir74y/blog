package ru.noir74.blog.test.configurations;

import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.repositories.CommentRepository;
import ru.noir74.blog.repositories.CommentRepositoryImpl;
import ru.noir74.blog.repositories.ItemRepository;
import ru.noir74.blog.repositories.TagRepository;
import ru.noir74.blog.services.CommentService;
import ru.noir74.blog.services.CommentServiceImpl;
import ru.noir74.blog.services.TagService;
import ru.noir74.blog.services.TagServiceImpl;

@Configuration
@Import({MappersTestConfig.class,ModelsTestConfig.class})
public class ServiceTestConfig {
    @Bean
    public TagRepository tagRepositoryMock() {
        return Mockito.mock(TagRepository.class);
    }

    @Bean
    public CommentRepository commentRepositoryMock() {
        return Mockito.mock(CommentRepository.class);
    }

    @Bean
    public ItemRepository itemRepositoryMock() {
        return Mockito.mock(ItemRepository.class);
    }

    @Bean
    public TagService tagService(TagRepository tagRepository, TagMapper tagMapper) {
        return new TagServiceImpl(tagRepository, tagMapper);
    }

    @Bean
    public CommentService tagService(CommentRepository commentRepository, CommentMapper commentMapper) {
        return new CommentServiceImpl(commentRepository, commentMapper);
    }

    interface TagServiceMock extends TagService {
    }

    static class TagServiceMockImpl extends TagServiceImpl implements TagService {
        public TagServiceMockImpl(TagRepository tagRepository, TagMapper tagMapper) {
            super(tagRepository, tagMapper);
        }
    }

    @Bean
    public TagServiceMock tagServiceMock() {
        return Mockito.mock(TagServiceMock.class);
    }
}
