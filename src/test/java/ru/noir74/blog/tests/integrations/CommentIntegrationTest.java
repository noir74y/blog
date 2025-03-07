package ru.noir74.blog.tests.integrations;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.generics.IntegrationTest;
import ru.noir74.blog.models.comment.Comment;
import ru.noir74.blog.models.post.Post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentIntegrationTest extends IntegrationTest {

    @Test
    void create() throws Exception {
        var postId = postService.create(Post.builder().title("title").message("message").build());

        mockMvc.perform(MockMvcRequestBuilders.post("/" + postId + "/comment")
                        .param("message", "comment")
                        .param("postId", String.valueOf(postId))
                )
                .andExpect(status().is3xxRedirection());

        assertEquals("comment", commentService.findAllByPostId(postId).getFirst().getMessage());
    }

    @Test
    void update() throws Exception {
        var postId = postService.create(Post.builder().title("title").message("message").build());
        var commentId = commentService.create(Comment.builder().message("comment").postId(postId).build());

        mockMvc.perform(MockMvcRequestBuilders.post("/" + postId + "/comment/" + commentId)
                        .param("id", String.valueOf(commentId))
                        .param("message", "commentUpdated")
                        .param("postId", String.valueOf(postId))
                )
                .andExpect(status().is3xxRedirection());

        assertEquals("commentUpdated", commentService.findById(commentId).getMessage());

    }

    @Test
    void delete() throws Exception {
        var postId = postService.create(Post.builder().title("title").message("message").build());
        var commentId = commentService.create(Comment.builder().message("comment").postId(postId).build());

        mockMvc.perform(MockMvcRequestBuilders.post("/" + postId + "/comment/" + commentId)
                        .param("_method", "delete")
                        .param("id", String.valueOf(commentId))
                )
                .andExpect(status().is3xxRedirection());

        assertThrows(NotFoundException.class, () -> commentService.findById(commentId));
    }

}
