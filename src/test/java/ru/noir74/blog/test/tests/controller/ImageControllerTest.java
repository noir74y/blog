package ru.noir74.blog.test.tests.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.models.post.Post;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImageControllerTest extends GenericControllerTest {

    @Test
    void getImage() throws Exception {
        var postId = postService.create(Post.builder().title("title").message("message").build());

        var mockMultipartFileToBeSaved = new MockMultipartFile(
                "file",
                "someFile.jpeg",
                "image/jpeg",
                "fileContent".getBytes());

        var post = postService.findById(postId);
        post.setFile(mockMultipartFileToBeSaved);
        postService.update(post);

        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(MockMvcRequestBuilders.get("/" + postId + "/image"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("fileContent", new String(mockHttpServletResponse.getContentAsByteArray()));

    }

    @Test
    void setImage() throws Exception {
        var postId = postService.create(Post.builder().title("title").message("message").build());

        var mockMultipartFileToBeSaved = new MockMultipartFile(
                "file",
                "someFile.jpeg",
                "image/jpeg",
                new byte[]{(byte) 0x00});

        mockMvc.perform(MockMvcRequestBuilders.multipart("/" + postId + "/image")
                        .file(new MockMultipartFile(
                                "file",
                                "someFile.jpeg",
                                "image/jpeg",
                                new byte[]{(byte) 0x00}))
                        .param("id", String.valueOf(postId))
                )
                .andExpect(status().isOk());

        var postImageEntity = postRepository.findImageById(postId);

        assertArrayEquals(mockMultipartFileToBeSaved.getBytes(), postImageEntity.getImage());
        assertEquals(mockMultipartFileToBeSaved.getOriginalFilename(), postImageEntity.getImageName());
    }
}
