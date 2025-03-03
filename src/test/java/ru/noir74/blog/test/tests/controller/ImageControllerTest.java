package ru.noir74.blog.test.tests.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.noir74.blog.models.item.Item;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImageControllerTest extends GenericControllerTest {

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
}
