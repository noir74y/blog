package ru.noir74.blog.test.tests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.mappers.PostImageMapper;
import ru.noir74.blog.mappers.PostMapper;
import ru.noir74.blog.models.post.*;
import ru.noir74.blog.repositories.intf.PostRepository;
import ru.noir74.blog.services.intf.PostService;
import ru.noir74.blog.test.generics.GenericServiceTest;
import ru.noir74.blog.test.tests.service.intf.CommentServiceMock;
import ru.noir74.blog.test.tests.service.intf.TagServiceMock;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PostServiceTest extends GenericServiceTest {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostImageMapper postImageMapper;
    @Autowired
    private PostRepository postRepositoryMock;
    @Autowired
    private TagServiceMock tagServiceMock;
    @Autowired
    private CommentServiceMock commentServiceMock;
    @Autowired
    private PostService postService;

    private Post post;
    private PostEntity postEntity;
    private List<PostBrief> postBriefs;
    private List<PostEntityBrief> postEntityBriefs;
    private PostImage postImage;
    private PostImageEntity postImageEntity;

    @BeforeEach
    void setUp() {
        var file = new MockMultipartFile(
                "name",
                "originalFilename.jpeg",
                "image/jpeg",
                new byte[]{(byte) 0x00});

        post = Post.builder()
                .id(0)
                .title("title")
                .message("message")
                .likes(0)
                .comments(new ArrayList<>())
                .tags(new ArrayList<>())
                .file(file)
                .changed(Timestamp.from(Instant.now())).build();
        postEntity = postMapper.model2entity(post);

        var postEntityBrief = PostEntityBrief.builder()
                .id(post.getId())
                .title(post.getTitle())
                .message(post.getMessage())
                .likes(post.getLikes())
                .commentsCounter(post.getLikes())
                .tagsCSV("")
                .build();
        postEntityBriefs = new LinkedList<>(List.of(postEntityBrief));
        postBriefs = postMapper.bulkEntityBrief2ModelBrief(postEntityBriefs);

        postImage = PostImage.builder().id(post.getId()).file(file).build();
        postImageEntity = postImageMapper.model2entity(postImage);

        reset(postRepositoryMock);
        reset(tagServiceMock);
        reset(commentServiceMock);
    }

    @Test
    void testFindPage() {
        when(postRepositoryMock.findByPage(0, 10)).thenReturn(postEntityBriefs);
        assertEquals(postBriefs, postService.findPage("0", "10", ""));
        verify(postRepositoryMock, times(1)).findByPage(0, 10);
    }

    @Test
    void testFindById() {
        when(postRepositoryMock.findById(0)).thenReturn(Optional.of(postEntity));
        assertEquals(post, postService.findById(0));
        verify(postRepositoryMock, times(1)).findById(0);
    }

    @Test
    void testFindById_NotFound() {
        when(postRepositoryMock.findById(1)).thenThrow(new NotFoundException("post is not found", String.valueOf(1)));
        assertThrows(NotFoundException.class, () -> postService.findById(1));
        verify(postRepositoryMock, times(1)).findById(1);
    }

    @Test
    void testFindImageById() throws IOException {
        when(postRepositoryMock.findImageById(post.getId())).thenReturn(postImageEntity);
        postService.findImageById(postImage);
        verify(postRepositoryMock, times(1)).findImageById(post.getId());
    }

    @Test
    void testCreate() throws IOException {
        when(postRepositoryMock.save(postEntity)).thenReturn(post.getId());
        doNothing().when(postRepositoryMock).saveImageById(postImageEntity);
        doNothing().when(tagServiceMock).attachTagsToPost(List.of(), post.getId());

        postService.create(post);

        verify(postRepositoryMock, times(1)).save(postEntity);
        verify(postRepositoryMock, times(1)).saveImageById(postImageEntity);
        verify(tagServiceMock, times(1)).attachTagsToPost(List.of(), post.getId());
    }

    @Test
    void testUpdate() throws IOException {
        when(postRepositoryMock.existsById(post.getId())).thenReturn(true);
        when(postRepositoryMock.save(postEntity)).thenReturn(post.getId());
        postService.update(post);
        verify(postRepositoryMock, times(1)).existsById(post.getId());
        verify(postRepositoryMock, times(1)).save(postEntity);
    }

    @Test
    void testUpdate_NotFound() {
        when(postRepositoryMock.existsById(post.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> postService.update(post));
        verify(postRepositoryMock, times(1)).existsById(post.getId());
    }

    @Test
    void testSetImageById() throws IOException {
        doNothing().when(postRepositoryMock).saveImageById(postImageEntity);
        postService.setImageById(postImage);
        verify(postRepositoryMock, times(1)).saveImageById(postImageEntity);
    }

    @Test
    void testDelete() {
        when(postRepositoryMock.existsById(post.getId())).thenReturn(true);
        doNothing().when(postRepositoryMock).deleteById(post.getId());
        postService.delete(post.getId());
        verify(postRepositoryMock, times(1)).existsById(post.getId());
        verify(postRepositoryMock, times(1)).deleteById(post.getId());
    }

    @Test
    void testDelete_NotFound() {
        when(postRepositoryMock.existsById(post.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> postService.delete(post.getId()));
        verify(postRepositoryMock, times(1)).existsById(post.getId());
    }
}
