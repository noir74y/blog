package ru.noir74.blog.configurations;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import ru.noir74.blog.generics.GenericTestConfig;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.PostImageMapper;
import ru.noir74.blog.mappers.PostMapper;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.repositories.intf.CommentRepository;
import ru.noir74.blog.repositories.intf.PostRepository;
import ru.noir74.blog.repositories.intf.TagRepository;
import ru.noir74.blog.services.impl.CommentServiceImpl;
import ru.noir74.blog.services.impl.PostServiceImpl;
import ru.noir74.blog.services.impl.TagServiceImpl;
import ru.noir74.blog.services.intf.CommentService;
import ru.noir74.blog.services.intf.PostService;
import ru.noir74.blog.services.intf.TagService;
import ru.noir74.blog.tests.services.intf.CommentServiceMock;
import ru.noir74.blog.tests.services.intf.TagServiceMock;

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
    public PostRepository postRepositoryMock() {
        return Mockito.mock(PostRepository.class);
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
    public PostService postService(PostRepository postRepositoryMock,
                                   PostMapper postMapper,
                                   PostImageMapper postImageMapper,
                                   TagServiceMock tagServiceMock,
                                   CommentServiceMock commentServiceMock) {
        return new PostServiceImpl(
                postRepositoryMock,
                postMapper,
                postImageMapper,
                tagServiceMock,
                commentServiceMock);
    }
}
