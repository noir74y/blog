package ru.noir74.blog.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.noir74.blog.models.item.ItemDtoReq;
import ru.noir74.blog.models.item.ItemDtoRespBrief;
import ru.noir74.blog.models.item.ItemMapper;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.services.CommentService;
import ru.noir74.blog.services.ItemService;
import ru.noir74.blog.services.TagService;
import ru.noir74.blog.validations.OnUpdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemMapper itemMapper;
    private final ItemService itemService;
    private final TagService tagService;
    private final CommentService commentService;

    @GetMapping
    public String getPage(Model model,
                          @RequestParam(defaultValue = "1", required = false, name = "page") String page,
                          @RequestParam(defaultValue = "10", required = false, name = "size") String size,
                          @RequestParam(defaultValue = "", required = false, name = "selectedTags") String selectedTags) {

        log.info("GET /items");

        List<ItemDtoRespBrief> posts = itemMapper.BulkModelBrief2dtoRespBrief(itemService.findPage(page, size, selectedTags));

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("posts", posts);
        model.addAttribute("selectedTags", new ArrayList<>(Arrays.stream(selectedTags.split(",")).toList()));
        model.addAttribute("allTags", tagService.findAll().stream().map(Tag::getName).toList());

        return "/items";
    }

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable("id") Integer id) {
        log.info("GET /items/{}", id);
        var itemDtoResp = itemMapper.model2dtoResp(itemService.findById(id));

        model.addAttribute("id", itemDtoResp.getId());
        model.addAttribute("title", itemDtoResp.getTitle());
        model.addAttribute("message", itemDtoResp.getMessage());
        model.addAttribute("likes", itemDtoResp.getLikes());
        model.addAttribute("itemSelectedTags", itemDtoResp.getTags().stream().map(Tag::getName).toList());
        model.addAttribute("allTags", tagService.findAll().stream().map(Tag::getName).toList());
        model.addAttribute("comments", commentService.findAllByItemId(id));

        return "/item";
    }

    @PostMapping
    public String create(@ModelAttribute ItemDtoReq dtoReq) {
        log.info("POST (for create) /items, dtoReq={}", dtoReq.toString());
        itemService.create(itemMapper.dtoReq2Model(dtoReq));
        return "redirect:/items";
    }

    @PostMapping(value = "/{id}")
    public String update(@ModelAttribute ItemDtoReq dtoReq, @PathVariable("id") Integer id) {
        log.info("POST (for update) /items/{}, dtoReq={}", id, dtoReq.toString());
        itemService.update(itemMapper.dtoReq2Model(dtoReq));
        return "redirect:/items";
    }

    @PostMapping(value = "/{id}", params = "_method=delete")
    public String delete(@PathVariable("id") Integer id) {
        log.info("POST (for delete) /items/{}", id);
        itemService.delete(id);
        return "redirect:/items";
    }
}