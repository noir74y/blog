package ru.noir74.blog.test.tests.mvc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.noir74.blog.models.item.Item;
import ru.noir74.blog.services.intf.ItemService;
import ru.noir74.blog.test.configurations.MvcTestConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private JdbcTemplate jdbcTemplate;
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        jdbcTemplate.execute("DELETE FROM items");
    }

    @Test
    void test() throws Exception {
        var newItemId = itemService.create(Item.builder().title("title").message("message").build());

        MvcResult result = mockMvc.perform(get("/items"))
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
        String postTitleValue = table.select("td").get(0).text();
        String postMessageValue = table.select("td").get(1).text();

        assertEquals(1, table.size());
        assertEquals("title", postTitleValue);
        assertEquals("message", postMessageValue);
    }
}
