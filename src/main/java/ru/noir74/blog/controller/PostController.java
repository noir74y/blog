package ru.noir74.blog.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.noir74.blog.model.mapper.PostMapper;
import ru.noir74.blog.service.PostService;
import ru.noir74.blog.model.dto.PostDtoReq;
import ru.noir74.blog.model.dto.PostDtoResp;
import ru.noir74.blog.util.validation.OnCreate;
import ru.noir74.blog.util.validation.OnUpdate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostMapper postMapper;
    private final PostService postService;

    @GetMapping("/{id}")
    public PostDtoResp get(@PathVariable Integer id) {
        log.info("GET /post/{}", id);
        return postMapper.model2dtoResp(postService.findById(id));
    }

    @PostMapping
    public PostDtoResp post(@Validated(OnCreate.class) @RequestBody PostDtoReq dtoReq) {
        log.info("POST /post, post={}", dtoReq.toString());
        return postMapper.model2dtoResp(postService.create(postMapper.dtoReq2Model(dtoReq)));
    }

    @PatchMapping
    public void patch(@Validated(OnUpdate.class) @RequestBody PostDtoReq dtoReq) {
        log.info("PATCH /post, post={}", dtoReq.toString());
        postService.update(postMapper.dtoReq2Model(dtoReq));
    }

    @DeleteMapping("/{id}")
    public void delete(@NotNull @PathVariable Integer id) {
        log.info("DELETE /post/{}", id);
        postService.delete(id);
    }
}