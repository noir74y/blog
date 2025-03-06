package ru.noir74.blog.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.models.post.Post;
import ru.noir74.blog.models.post.PostDtoReq;
import ru.noir74.blog.models.post.PostDtoResp;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest extends GenericControllerTest {
    @Test
    void getPage() throws Exception {
        var page = "1";
        var size = "1";
        var selectedTags = "";

        when(postService.findPage(page, size, selectedTags)).thenReturn(Collections.emptyList());
        when(postMapper.bulkModelBrief2DtoRespBrief(Collections.emptyList())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .param("page", page)
                        .param("size", size)
                        .param("selectedTags", selectedTags))
                .andExpect(status().isOk());

        verify(postService).findPage(page, size, selectedTags);
    }

    @Test
    void get() throws Exception {
        var postId = 1;

        when(postService.findById(postId)).thenReturn(Post.builder().build());
        when(postMapper.model2dtoResp(Post.builder().build())).thenReturn(PostDtoResp.builder().tags(Collections.emptyList()).build());

        mockMvc.perform(MockMvcRequestBuilders.get("/" + postId))
                .andExpect(status().isOk());

        verify(postService).findById(1);
    }

    @Test
    void create() throws Exception {
        var title = "title";
        var message = "message";
        var postNewTagsCsv = "";

        var mockMultipartFileToBeSaved = new MockMultipartFile(
                "file",
                "someFile.jpeg",
                "image/jpeg",
                new byte[]{(byte) 0x00});

        mockMvc.perform(MockMvcRequestBuilders.multipart("/")
                        .file(mockMultipartFileToBeSaved)
                        .param("title", title)
                        .param("message", message)
                        .param("postNewTagsCsv", postNewTagsCsv)
                )
                .andExpect(status().is3xxRedirection());

        verify(postService).create(postMapper.dtoReq2Model(
                PostDtoReq.builder()
                        .title(title)
                        .message(title)
                        .postNewTagsCsv(postNewTagsCsv)
                        .file(mockMultipartFileToBeSaved)
                        .build()));
    }

    @Test
    void update() throws Exception {
        var postId = 1;
        var title = "title";
        var message = "message";
        var postNewTagsCsv = "";

        var mockMultipartFileToBeSaved = new MockMultipartFile(
                "file",
                "someFile.jpeg",
                "image/jpeg",
                new byte[]{(byte) 0x00});

        mockMvc.perform(MockMvcRequestBuilders.multipart("/" + postId)
                        .file(mockMultipartFileToBeSaved)
                        .param("title", title)
                        .param("message", message)
                        .param("postNewTagsCsv", postNewTagsCsv)
                )
                .andExpect(status().is3xxRedirection());

        verify(postService).update(postMapper.dtoReq2Model(
                PostDtoReq.builder()
                        .id(postId)
                        .title(title)
                        .message(title)
                        .postNewTagsCsv(postNewTagsCsv)
                        .file(mockMultipartFileToBeSaved)
                        .build()));
    }

    @Test
    void delete() throws Exception {
        var postId = 1;

        mockMvc.perform(MockMvcRequestBuilders.post("/" + postId).param("_method", "delete"))
                .andExpect(status().is3xxRedirection());

        verify(postService).delete(postId);
    }

}
