package ru.noir74.blog.test.tests.mvc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.models.item.Item;
import ru.noir74.blog.repositories.intf.ItemRepository;
import ru.noir74.blog.services.intf.ItemService;
import ru.noir74.blog.test.configurations.MvcTestConfig;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@SpringJUnitConfig(MvcTestConfig.class)
@TestPropertySource(locations = "classpath:test-application.properties")
public class ItemControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        jdbcTemplate.execute("DELETE FROM items");
    }

    @Test
    void getPage() throws Exception {
        var newItemId = itemService.create(Item.builder().title("title").message("message").build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/items"))
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
        var newItemId = itemService.create(Item.builder().title("title").message("message").build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/items/" + newItemId))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/item"))
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

        mockMvc.perform(MockMvcRequestBuilders.multipart("/items")
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

        mockMvc.perform(MockMvcRequestBuilders.multipart("/items/" + String.valueOf(itemId))
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

        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(MockMvcRequestBuilders.get("/items/" + itemId + "/image"))
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

        mockMvc.perform(MockMvcRequestBuilders.multipart("/items/" + itemId + "/image")
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

        mockMvc.perform(MockMvcRequestBuilders.post("/items/" + itemId).param("_method", "delete"))
                .andExpect(status().is3xxRedirection());

        assertThrows(NotFoundException.class, () -> itemService.findById(itemId));
    }
}
