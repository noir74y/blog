package ru.noir74.blog.tests.integrations;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.generics.IntegrationTest;
import ru.noir74.blog.models.post.Post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LikeIntegrationTest extends IntegrationTest {
    @Test
    void addLike() throws Exception {
        var postId = postService.create(Post.builder().title("title").message("message").build());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/" + postId + "/addLike")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" "))
                .andExpect(status().isOk());
        var post = postService.findById(postId);
        assertEquals(1, post.getLikes());
    }
}
