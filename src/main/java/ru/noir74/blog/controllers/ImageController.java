package ru.noir74.blog.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.noir74.blog.models.post.PostImage;
import ru.noir74.blog.services.intf.PostService;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {
    private final PostService postService;

    @GetMapping("{id}/image")
    public void getImage(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        log.info("GET /{}/image", id);
        postService.findImageById(PostImage.builder().id(id).response(response).build());
    }

    @PostMapping("{id}/image")
    public String setImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        log.info("POST /{}/image", id);
        postService.setImageById(PostImage.builder().id(id).file(file).build());
        return "post";
    }

    @GetMapping(value = "/favicon.ico", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> favicon() {
        Resource resource = new ClassPathResource("static/favicon.ico");
        return ResponseEntity.ok(resource);
    }
}