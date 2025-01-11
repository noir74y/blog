package ru.noir74.blog.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.noir74.blog.model.mapper.ItemMapper;
import ru.noir74.blog.service.ItemService;
import ru.noir74.blog.model.dto.ItemDtoReq;
import ru.noir74.blog.model.dto.ItemDtoResp;
import ru.noir74.blog.util.validation.OnCreate;
import ru.noir74.blog.util.validation.OnUpdate;

@Slf4j
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ItemDtoResp get(@PathVariable Integer id) {
        log.info("GET /{}", id);
        return itemMapper.model2dtoResp(itemService.get(id));
    }

    @PostMapping
    public ItemDtoResp create(@Validated(OnCreate.class) @RequestBody ItemDtoReq dtoReq) {
        log.info("POST /item, item={}", dtoReq.toString());
        return itemMapper.model2dtoResp(itemService.create(itemMapper.dtoReq2Model(dtoReq)));
    }

    @PatchMapping
    public void update(@Validated(OnUpdate.class) @RequestBody ItemDtoReq dtoReq) {
        log.info("PATCH /item, item={}", dtoReq.toString());
        itemService.update(itemMapper.dtoReq2Model(dtoReq));
    }

    @DeleteMapping("/{id}")
    public void delete(@NotNull @PathVariable Integer id) {
        log.info("DELETE /item/{}", id);
        itemService.delete(id);
    }

    @PatchMapping("/addLike/{id}")
    public void addLike(@NotNull @PathVariable Integer id) {
        log.info("ADD LIKE /item/{}", id);
        itemService.addLike(id);
    }

    @PatchMapping("/removeLike/{id}")
    public void removeLike(@NotNull @PathVariable Integer id) {
        log.info("REMOVE LIKE /item/{}", id);
        itemService.removeLike(id);
    }
}