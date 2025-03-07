package ru.noir74.blog.tests.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.generics.ControllerTest;
import ru.noir74.blog.models.comment.Comment;
import ru.noir74.blog.models.comment.CommentDtoReq;
import ru.noir74.blog.models.post.Post;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends ControllerTest {
    @Test
    void create() throws Exception {
        var postId = 1;
        var message = "message";

        mockMvc.perform(MockMvcRequestBuilders.post("/" + postId + "/comment")
                        .param("message", message)
                        .param("postId", String.valueOf(postId))
                )
                .andExpect(status().is3xxRedirection());

        verify(commentService).create(commentMapper.dtoReq2Model(CommentDtoReq.builder().message(message).postId(postId).build()));

    }

    @Test
    void update() throws Exception {
        var postId = 1;
        var message = "message";
        var commentId = 1;

        mockMvc.perform(MockMvcRequestBuilders.post("/" + postId + "/comment/" + commentId)
                        .param("id", String.valueOf(commentId))
                        .param("message", "commentUpdated")
                        .param("postId", String.valueOf(postId))
                )
                .andExpect(status().is3xxRedirection());

        verify(commentService).update(commentMapper.dtoReq2Model(CommentDtoReq.builder().id(commentId).message(message).postId(postId).build()));
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

        verify(commentService).delete(commentId);
    }
}
