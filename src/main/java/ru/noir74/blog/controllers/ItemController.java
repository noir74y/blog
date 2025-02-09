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
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemMapper itemMapper;
    private final ItemService itemService;
    private final TagService tagService;
    private final CommentService commentService;

    @GetMapping("/list")
    public String getPage(Model model,
                          @RequestParam(defaultValue = "1", required = false, name = "page") String page,
                          @RequestParam(defaultValue = "10", required = false, name = "size") String size,
                          @RequestParam(defaultValue = "", required = false, name = "selectedTags") String selectedTags) {

        log.info("GET /item/list");

        List<ItemDtoRespBrief> posts = itemMapper.BulkModelBrief2dtoRespBrief(itemService.findPage(page, size, selectedTags));

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("posts", posts);
        model.addAttribute("selectedTags", new ArrayList<>(Arrays.stream(selectedTags.split(",")).toList()));
        model.addAttribute("allTags", tagService.findAll().stream().map(Tag::getName).toList());

        return "items";
    }

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable("id") Integer id) {
        log.info("GET /item/{}", id);
        var itemDtoResp = itemMapper.model2dtoResp(itemService.findById(id));

        model.addAttribute("id", itemDtoResp.getId());
        model.addAttribute("title", itemDtoResp.getTitle());
        model.addAttribute("message", itemDtoResp.getMessage());
        model.addAttribute("likes", itemDtoResp.getLikes());
        model.addAttribute("itemSelectedTags", itemDtoResp.getTags().stream().map(Tag::getName).toList());
        model.addAttribute("allTags", tagService.findAll().stream().map(Tag::getName).toList());
        model.addAttribute("comments", commentService.findAllByItemId(id));

        return "item";
    }

    @PostMapping
    public String create(@ModelAttribute ItemDtoReq dtoReq) {
        log.info("POST /item, dtoReq={}", dtoReq.toString());
        itemService.create(itemMapper.dtoReq2Model(dtoReq));
        return "redirect:/item/list";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute ItemDtoReq dtoReq, @PathVariable("id") Integer id) {
        log.info("PATCH /item/{}, dtoReq={}", id, dtoReq.toString());
        itemService.update(itemMapper.dtoReq2Model(dtoReq));
        return "redirect:/item/list";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        log.info("DELETE /item/{}", id);
        itemService.delete(id);
        return "redirect:/item/list";
    }

    @PatchMapping("/{id}/addLike")
    public String addLike(@PathVariable Integer id) {
        log.info("PATCH /item/{}/addLike", id);
        itemService.addLike(id);
        return "redirect:/item/list";
    }

    @PatchMapping("/{id}/removeLike")
    public String removeLike(@PathVariable Integer id) {
        log.info("PATCH /item/{}/removeLike", id);
        itemService.removeLike(id);
        return "redirect:/item/list";
    }
}