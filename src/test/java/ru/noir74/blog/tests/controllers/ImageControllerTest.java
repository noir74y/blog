package ru.noir74.blog.tests.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.generics.ControllerTest;
import ru.noir74.blog.models.post.PostImage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImageControllerTest extends ControllerTest {
    Integer postId;
    MockMultipartFile mockMultipartFile;

    @BeforeEach
    void setUp() {
        postId = 1;
        mockMultipartFile = new MockMultipartFile(
                "file",
                "someFile.jpeg",
                "image/jpeg",
                new byte[]{(byte) 0x00});
    }

    @Test
    void getImage() throws Exception {
        when(postService.findImageById(postId)).thenReturn(PostImage.builder()
                .id(postId)
                .image(mockMultipartFile.getBytes())
                .imageName(mockMultipartFile.getOriginalFilename())
                .build());

        byte[] byteReceived = mockMvc.perform(MockMvcRequestBuilders.get("/" + postId + "/image"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        verify(postService).findImageById(postId);
        assertArrayEquals(mockMultipartFile.getBytes(), byteReceived);
    }

    @Test
    void setImage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/" + postId + "/image")
                        .file(mockMultipartFile)
                        .param("id", String.valueOf(postId))
                )
                .andExpect(status().isOk());

        verify(postService).setImageById(PostImage.builder()
                .id(postId)
                .image(mockMultipartFile.getBytes())
                .imageName(mockMultipartFile.getOriginalFilename())
                .build());
    }
}
