package ru.noir74.blog.test.tests.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.models.item.Item;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ItemControllerTest extends GenericControllerTest {

    @Test
    void getPage() throws Exception {
        var itemId = itemService.create(Item.builder().title("title").message("message").build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("items"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("size"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("selectedTags"))
                .andExpect(model().attributeExists("allTags"))
                .andExpect(status().isOk())
                .andReturn();

        Document document = Jsoup.parse(result.getResponse().getContentAsString());
        Elements table = document.select("#itemsTable");
        String valueTitle = table.select("td").get(0).text();
        String valueMessage = table.select("td").get(1).text();

        assertEquals(1, table.size());
        assertEquals("title", valueTitle);
        assertEquals("message", valueMessage);
    }

    @Test
    void get() throws Exception {
        var itemId = itemService.create(Item.builder().title("title").message("message").build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/" + itemId))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("item"))
                .andExpect(model().attributeExists("id"))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attributeExists("likes"))
                .andExpect(model().attributeExists("itemSelectedTags"))
                .andExpect(model().attributeExists("allTags"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(status().isOk())
                .andReturn();

        Document document = Jsoup.parse(result.getResponse().getContentAsString());

        Element form = document.select("#itemForm").first();
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
                        .param("itemNewTagsCsv", "")
                )
                .andExpect(status().is3xxRedirection());

        var newItemId = itemService.findPage("1", "10", "").getFirst().getId();
        var newItem = itemService.findById(newItemId);

        assertEquals("title", newItem.getTitle());
        assertEquals("message", newItem.getMessage());

        var itemImageEntity = itemRepository.findImageById(newItemId);

        assertArrayEquals(mockMultipartFileToBeSaved.getBytes(), itemImageEntity.getImage());
        assertEquals(mockMultipartFileToBeSaved.getOriginalFilename(), itemImageEntity.getImageName());
    }

    @Test
    void update() throws Exception {
        var itemId = itemService.create(Item.builder().title("title").message("message").build());

        var mockMultipartFileToBeSaved = new MockMultipartFile(
                "file",
                "someFile.jpeg",
                "image/jpeg",
                new byte[]{(byte) 0x00});

        mockMvc.perform(MockMvcRequestBuilders.multipart("/" + itemId)
                        .file(new MockMultipartFile(
                                "file",
                                "someFile.jpeg",
                                "image/jpeg",
                                new byte[]{(byte) 0x00}))
                        .param("id", String.valueOf(itemId))
                        .param("title", "titleUpdated")
                        .param("message", "messageUpdated")
                        .param("itemNewTagsCsv", "")
                )
                .andExpect(status().is3xxRedirection());

        var newItem = itemService.findById(itemId);

        assertEquals("titleUpdated", newItem.getTitle());
        assertEquals("messageUpdated", newItem.getMessage());

        var itemImageEntity = itemRepository.findImageById(itemId);

        assertArrayEquals(mockMultipartFileToBeSaved.getBytes(), itemImageEntity.getImage());
        assertEquals(mockMultipartFileToBeSaved.getOriginalFilename(), itemImageEntity.getImageName());
    }

    @Test
    void getImage() throws Exception {
        var itemId = itemService.create(Item.builder().title("title").message("message").build());

        var mockMultipartFileToBeSaved = new MockMultipartFile(
                "file",
                "someFile.jpeg",
                "image/jpeg",
                "fileContent".getBytes());

        var item = itemService.findById(itemId);
        item.setFile(mockMultipartFileToBeSaved);
        itemService.update(item);

        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(MockMvcRequestBuilders.get("/" + itemId + "/image"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("fileContent", new String(mockHttpServletResponse.getContentAsByteArray()));

    }

    @Test
    void setImage() throws Exception {
        var itemId = itemService.create(Item.builder().title("title").message("message").build());

        var mockMultipartFileToBeSaved = new MockMultipartFile(
                "file",
                "someFile.jpeg",
                "image/jpeg",
                new byte[]{(byte) 0x00});

        mockMvc.perform(MockMvcRequestBuilders.multipart("/" + itemId + "/image")
                        .file(new MockMultipartFile(
                                "file",
                                "someFile.jpeg",
                                "image/jpeg",
                                new byte[]{(byte) 0x00}))
                        .param("id", String.valueOf(itemId))
                )
                .andExpect(status().isOk());

        var itemImageEntity = itemRepository.findImageById(itemId);

        assertArrayEquals(mockMultipartFileToBeSaved.getBytes(), itemImageEntity.getImage());
        assertEquals(mockMultipartFileToBeSaved.getOriginalFilename(), itemImageEntity.getImageName());
    }

    @Test
    void delete() throws Exception {
        var itemId = itemService.create(Item.builder().title("title").message("message").build());

        mockMvc.perform(MockMvcRequestBuilders.post("/" + itemId).param("_method", "delete"))
                .andExpect(status().is3xxRedirection());

        assertThrows(NotFoundException.class, () -> itemService.findById(itemId));
    }

}
