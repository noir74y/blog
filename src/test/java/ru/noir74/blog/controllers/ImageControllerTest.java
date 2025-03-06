package ru.noir74.blog.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.models.post.PostImage;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ImageController.class)
public class ImageControllerTest extends GenericControllerTest {
    Integer postId;

    @BeforeEach
    void setUp() {
        postId = 1;
    }

    @Test
    void getImage() throws Exception {

//        var mockMultipartFile = new MockMultipartFile(
//                "file",
//                "someFile.jpeg",
//                "image/jpeg",
//                new byte[]{(byte) 0x00});
//
//        when(postService.findImageById(PostImage.builder().id(postId).response(response).build())).thenReturn(Post.builder().build());

        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(MockMvcRequestBuilders.get("/" + postId + "/image"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //verify(postService).findImageById(PostImage.builder().id(postId).response(mockHttpServletResponse).build());
    }

    @Test
    void setImage() throws Exception {
        var mockMultipartFileToBeSaved = new MockMultipartFile(
                "file",
                "someFile.jpeg",
                "image/jpeg",
                new byte[]{(byte) 0x00});

        mockMvc.perform(MockMvcRequestBuilders.multipart("/" + postId + "/image")
                        .file(mockMultipartFileToBeSaved)
                        .param("id", String.valueOf(postId))
                )
                .andExpect(status().isOk());

        verify(postService).setImageById(PostImage.builder().id(postId).file(mockMultipartFileToBeSaved).build());
    }
}
