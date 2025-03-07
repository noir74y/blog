package ru.noir74.blog.tests.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.generics.ControllerTest;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LikeControllerTest extends ControllerTest {
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
