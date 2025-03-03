package ru.noir74.blog.test.tests.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.models.item.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LikeControllerTest extends GenericControllerTest {

    @Test
    void addLike() throws Exception {
        var itemId = itemService.create(Item.builder().title("title").message("message").build());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/" + itemId + "/addLike")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" "))
                .andExpect(status().isOk());
        var item = itemService.findById(itemId);
        assertEquals(1, item.getLikes());
    }
}
