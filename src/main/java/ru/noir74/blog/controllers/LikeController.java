package ru.noir74.blog.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.noir74.blog.services.intf.PostService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {
    private final PostService postService;

    @PostMapping("{id}/addLike")
    public void addLike(@RequestBody String text, @PathVariable("id") Integer id) {
        log.info("POST /{}/addLike", id);
        postService.addLike(id);
    }
}