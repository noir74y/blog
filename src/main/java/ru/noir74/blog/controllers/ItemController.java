package ru.noir74.blog.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.noir74.blog.models.item.ItemDtoReq;
import ru.noir74.blog.models.item.ItemDtoResp;
import ru.noir74.blog.models.item.ItemDtoRespBrief;
import ru.noir74.blog.models.item.ItemMapper;
import ru.noir74.blog.services.ItemService;
import ru.noir74.blog.validations.OnCreate;
import ru.noir74.blog.validations.OnUpdate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @GetMapping("/list")
    public String getPage(Model model,
                          @RequestParam(defaultValue = "1", required = false, name = "page") String page,
                          @RequestParam(defaultValue = "10", required = false, name = "size") String size,
                          @RequestParam(defaultValue = "", required = false, name = "tags") String tags) {

        List<ItemDtoRespBrief> posts = itemMapper.BulkModelBrief2dtoRespBrief(itemService.getPage(
                page,
                size,
                new HashSet<>(new ArrayList<>(Arrays.asList(tags.split(","))))));

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", posts);

        model.addAttribute("tags", posts.stream()
                .flatMap(obj -> obj.getTags().stream())
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new)));

        return "items";
    }

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
    public void delete(@PathVariable Integer id) {
        log.info("DELETE /item/{}", id);
        itemService.delete(id);
    }

    @PatchMapping("/addLike/{id}")
    public void addLike(@PathVariable Integer id) {
        log.info("PATCH /item/addLike/{}", id);
        itemService.addLike(id);
    }

    @PatchMapping("/removeLike/{id}")
    public void removeLike(@PathVariable Integer id) {
        log.info("PATCH /item/removeLike/{}", id);
        itemService.removeLike(id);
    }
}