package ru.noir74.blog.test.configurations;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.ItemImageMapper;
import ru.noir74.blog.mappers.ItemMapper;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.repositories.intf.CommentRepository;
import ru.noir74.blog.repositories.intf.ItemRepository;
import ru.noir74.blog.repositories.intf.TagRepository;
import ru.noir74.blog.services.impl.CommentServiceImpl;
import ru.noir74.blog.services.impl.ItemServiceImpl;
import ru.noir74.blog.services.impl.TagServiceImpl;
import ru.noir74.blog.services.intf.CommentService;
import ru.noir74.blog.services.intf.ItemService;
import ru.noir74.blog.services.intf.TagService;
import ru.noir74.blog.test.tests.service.intf.CommentServiceMock;
import ru.noir74.blog.test.tests.service.intf.TagServiceMock;

@Configuration
public class ServiceTestConfig extends GenericTestConfig {
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
    public TagServiceMock tagServiceMock() {
        return Mockito.mock(TagServiceMock.class);
    }

    @Bean
    public CommentServiceMock commentServiceMock() {
        return Mockito.mock(CommentServiceMock.class);
    }

    @Bean
    public TagService tagService(TagRepository tagRepositoryMock,
                                 TagMapper tagMapper) {
        return new TagServiceImpl(
                tagRepositoryMock,
                tagMapper);
    }

    @Bean
    public CommentService commentService(CommentRepository commentRepositoryMock,
                                         CommentMapper commentMapper) {
        return new CommentServiceImpl(
                commentRepositoryMock,
                commentMapper);
    }

    @Bean
    public ItemService itemService(ItemRepository itemRepositoryMock,
                                   ItemMapper itemMapper,
                                   ItemImageMapper itemImageMapper,
                                   TagServiceMock tagServiceMock,
                                   CommentServiceMock commentServiceMock) {
        return new ItemServiceImpl(
                itemRepositoryMock,
                itemMapper,
                itemImageMapper,
                tagServiceMock,
                commentServiceMock);
    }
}
