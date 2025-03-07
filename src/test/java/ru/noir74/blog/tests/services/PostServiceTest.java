package ru.noir74.blog.tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.generics.ServiceTest;
import ru.noir74.blog.models.post.*;

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

public class PostServiceTest extends ServiceTest {
    private Post post;
    private PostEntity postEntity;
    private List<PostBrief> postBriefs;
    private List<PostEntityBrief> postEntityBriefs;
    private PostImage postImage;
    private PostImageEntity postImageEntity;

    @BeforeEach
    void setUp() throws IOException {
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

        postImage = PostImage.builder()
                .id(post.getId())
                .image(post.getFile().getBytes())
                .imageName(post.getFile().getOriginalFilename()).build();
        postImageEntity = postImageMapper.model2entity(postImage);

        reset(postRepository);
        reset(tagServiceMock);
        reset(commentServiceMock);
    }

    @Test
    void testFindPage() {
        when(postRepository.findByPage(0, 10)).thenReturn(postEntityBriefs);
        assertEquals(postBriefs, postService.findPage("0", "10", ""));
        verify(postRepository, times(1)).findByPage(0, 10);
    }

    @Test
    void testFindById() {
        when(postRepository.findById(0)).thenReturn(Optional.of(postEntity));
        assertEquals(post, postService.findById(0));
        verify(postRepository, times(1)).findById(0);
    }

    @Test
    void testFindById_NotFound() {
        when(postRepository.findById(1)).thenThrow(new NotFoundException("post is not found", String.valueOf(1)));
        assertThrows(NotFoundException.class, () -> postService.findById(1));
        verify(postRepository, times(1)).findById(1);
    }

    @Test
    void testFindImageById() throws IOException {
        when(postRepository.findImageById(post.getId())).thenReturn(postImageEntity);
        postService.findImageById(postImage.getId());
        verify(postRepository, times(1)).findImageById(post.getId());
    }

    @Test
    void testCreate() throws IOException {
        when(postRepository.save(postEntity)).thenReturn(post.getId());
        doNothing().when(postRepository).saveImageById(postImageEntity);
        doNothing().when(tagServiceMock).attachTagsToPost(List.of(), post.getId());

        postService.create(post);

        verify(postRepository, times(1)).save(postEntity);
        verify(postRepository, times(1)).saveImageById(postImageEntity);
        verify(tagServiceMock, times(1)).attachTagsToPost(List.of(), post.getId());
    }

    @Test
    void testUpdate() throws IOException {
        when(postRepository.existsById(post.getId())).thenReturn(true);
        when(postRepository.save(postEntity)).thenReturn(post.getId());
        postService.update(post);
        verify(postRepository, times(1)).existsById(post.getId());
        verify(postRepository, times(1)).save(postEntity);
    }

    @Test
    void testUpdate_NotFound() {
        when(postRepository.existsById(post.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> postService.update(post));
        verify(postRepository, times(1)).existsById(post.getId());
    }

    @Test
    void testSetImageById() throws IOException {
        doNothing().when(postRepository).saveImageById(postImageEntity);
        postService.setImageById(postImage);
        verify(postRepository, times(1)).saveImageById(postImageEntity);
    }

    @Test
    void testDelete() {
        when(postRepository.existsById(post.getId())).thenReturn(true);
        doNothing().when(postRepository).deleteById(post.getId());
        postService.delete(post.getId());
        verify(postRepository, times(1)).existsById(post.getId());
        verify(postRepository, times(1)).deleteById(post.getId());
    }

    @Test
    void testDelete_NotFound() {
        when(postRepository.existsById(post.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> postService.delete(post.getId()));
        verify(postRepository, times(1)).existsById(post.getId());
    }
}
