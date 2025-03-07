package ru.noir74.blog.tests.integrations;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.generics.IntegrationTest;
import ru.noir74.blog.models.post.Post;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PostIntegrationTest extends IntegrationTest {

    @Test
    void getPage() throws Exception {
        var postId = postService.create(Post.builder().title("title").message("message").build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("posts"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("size"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("selectedTags"))
                .andExpect(model().attributeExists("allTags"))
                .andExpect(status().isOk())
                .andReturn();

        Document document = Jsoup.parse(result.getResponse().getContentAsString());
        Elements table = document.select("#postsTable");
        String valueTitle = table.select("td").get(0).text();
        String valueMessage = table.select("td").get(1).text();

        assertEquals(1, table.size());
        assertEquals("title", valueTitle);
        assertEquals("message", valueMessage);
    }

    @Test
    void get() throws Exception {
        var postId = postService.create(Post.builder().title("title").message("message").build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/" + postId))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("id"))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attributeExists("likes"))
                .andExpect(model().attributeExists("postSelectedTags"))
                .andExpect(model().attributeExists("allTags"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(status().isOk())
                .andReturn();

        Document document = Jsoup.parse(result.getResponse().getContentAsString());

        Element form = document.select("#postForm").first();
        assert form != null;
        String valueTitle = Objects.requireNonNull(form.select("#title").first()).attr("value");
        String valueMessage = form.select("#message").text();

        assertEquals("title", valueTitle);
        assertEquals("message", valueMessage);
    }

    @Test
    void create() throws Exception {
        var mockMultipartFileToBeSaved = new MockMultipartFile(
                "file",
                "someFile.jpeg",
                "image/jpeg",
                new byte[]{(byte) 0x00});

        mockMvc.perform(MockMvcRequestBuilders.multipart("/")
                        .file(mockMultipartFileToBeSaved)
                        .param("title", "title")
                        .param("message", "message")
                        .param("postNewTagsCsv", "")
                )
                .andExpect(status().is3xxRedirection());

        var newPostId = postService.findPage("1", "10", "").getFirst().getId();
        var newPost = postService.findById(newPostId);

        assertEquals("title", newPost.getTitle());
        assertEquals("message", newPost.getMessage());

        var postImageEntity = postRepository.findImageById(newPostId);

        assertArrayEquals(mockMultipartFileToBeSaved.getBytes(), postImageEntity.getImage());
        assertEquals(mockMultipartFileToBeSaved.getOriginalFilename(), postImageEntity.getImageName());
    }

    @Test
    void update() throws Exception {
        var postId = postService.create(Post.builder().title("title").message("message").build());

        var mockMultipartFileToBeSaved = new MockMultipartFile(
                "file",
                "someFile.jpeg",
                "image/jpeg",
                new byte[]{(byte) 0x00});

        mockMvc.perform(MockMvcRequestBuilders.multipart("/" + postId)
                        .file(new MockMultipartFile(
                                "file",
                                "someFile.jpeg",
                                "image/jpeg",
                                new byte[]{(byte) 0x00}))
                        .param("id", String.valueOf(postId))
                        .param("title", "titleUpdated")
                        .param("message", "messageUpdated")
                        .param("postNewTagsCsv", "")
                )
                .andExpect(status().is3xxRedirection());

        var newPost = postService.findById(postId);

        assertEquals("titleUpdated", newPost.getTitle());
        assertEquals("messageUpdated", newPost.getMessage());

        var postImageEntity = postRepository.findImageById(postId);

        assertArrayEquals(mockMultipartFileToBeSaved.getBytes(), postImageEntity.getImage());
        assertEquals(mockMultipartFileToBeSaved.getOriginalFilename(), postImageEntity.getImageName());
    }

    @Test
    void delete() throws Exception {
        var postId = postService.create(Post.builder().title("title").message("message").build());

        mockMvc.perform(MockMvcRequestBuilders.post("/" + postId).param("_method", "delete"))
                .andExpect(status().is3xxRedirection());

        assertThrows(NotFoundException.class, () -> postService.findById(postId));
    }

}
