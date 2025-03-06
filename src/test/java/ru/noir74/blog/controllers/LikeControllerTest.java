package ru.noir74.blog.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LikeController.class)
public class LikeControllerTest extends GenericControllerTest {
    @Test
    void addLike() throws Exception {
        var postId = 1;

        mockMvc.perform(MockMvcRequestBuilders.multipart("/" + postId + "/addLike")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" "))
                .andExpect(status().isOk());

        verify(postService).addLike(postId);
    }
}
