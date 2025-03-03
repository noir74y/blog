package ru.noir74.blog.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.noir74.blog.models.item.ItemImage;
import ru.noir74.blog.services.intf.ItemService;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ItemService itemService;

    @GetMapping("{id}/image")
    public void getImage(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        log.info("GET /{}/image", id);
        itemService.findImageById(ItemImage.builder().id(id).response(response).build());
    }

    @PostMapping("{id}/image")
    public String setImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        log.info("POST /{}/image", id);
        itemService.setImageById(ItemImage.builder().id(id).file(file).build());
        return "item";
    }
}