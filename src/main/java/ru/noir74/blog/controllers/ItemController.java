package ru.noir74.blog.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.ItemMapper;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.models.comment.CommentDtoReq;
import ru.noir74.blog.models.item.ItemDtoReq;
import ru.noir74.blog.models.item.ItemDtoRespBrief;
import ru.noir74.blog.models.item.ItemImage;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagDtoResp;
import ru.noir74.blog.services.intf.CommentService;
import ru.noir74.blog.services.intf.ItemService;
import ru.noir74.blog.services.intf.TagService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
//@RequestMapping("")
@RequiredArgsConstructor
public class ItemController {
    private final ItemMapper itemMapper;
    private final ItemService itemService;
    private final TagService tagService;
    private final TagMapper tagMapper;
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping
    public String getPage(Model model,
                          @RequestParam(defaultValue = "1", required = false, name = "page") String page,
                          @RequestParam(defaultValue = "10", required = false, name = "size") String size,
                          @RequestParam(defaultValue = "", required = false, name = "selectedTags") String selectedTags) {

        log.info("GET /");

        List<ItemDtoRespBrief> posts = itemMapper.bulkModelBrief2DtoRespBrief(itemService.findPage(page, size, selectedTags));

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("posts", posts);
        model.addAttribute("selectedTags", new ArrayList<>(Arrays.stream(selectedTags.split(",")).toList()));
        model.addAttribute("allTags", tagService.findAll().stream().map(Tag::getName).toList());

        return "items";
    }

    @GetMapping("{id}")
    public String get(Model model, @PathVariable("id") Integer id) {
        log.info("GET /{}", id);
        var itemDtoResp = itemMapper.model2dtoResp(itemService.findById(id));

        model.addAttribute("id", itemDtoResp.getId());
        model.addAttribute("title", itemDtoResp.getTitle());
        model.addAttribute("message", itemDtoResp.getMessage());
        model.addAttribute("likes", itemDtoResp.getLikes());
        model.addAttribute("itemSelectedTags", itemDtoResp.getTags().stream().map(Tag::getName).toList());
        model.addAttribute("allTags", tagMapper.bulkModel2DtoResp(tagService.findAll()).stream().map(TagDtoResp::getName).toList());
        model.addAttribute("comments", commentMapper.bulkModel2DtoResp(commentService.findAllByItemId(id)));

        return "item";
    }

    @GetMapping("{id}/image")
    public void getImage(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        log.info("GET /{}/image", id);
        itemService.findImageById(ItemImage.builder().id(id).response(response).build());
    }

    @PostMapping(produces = "text/plain;charset=UTF-8")
    public String create(@ModelAttribute ItemDtoReq dtoReq) throws IOException {
        log.info("POST (for item create) /, dtoReq={}", dtoReq.toString());
        itemService.create(itemMapper.dtoReq2Model(dtoReq));
        return "redirect:/";
    }

    @PostMapping(value = "{id}")
    public String update(@ModelAttribute ItemDtoReq dtoReq, @PathVariable("id") Integer id) throws IOException {
        log.info("POST (for item update) /{}, dtoReq={}", id, dtoReq.toString());
        itemService.update(itemMapper.dtoReq2Model(dtoReq));
        return "redirect:" + dtoReq.getId();
    }

    @PostMapping("{id}/image")
    public String setImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        log.info("POST /{}/image", id);
        itemService.setImageById(ItemImage.builder().id(id).file(file).build());
        return "item";
    }

    @PostMapping(value = "{id}", params = "_method=delete")
    public String delete(@PathVariable("id") Integer id) {
        log.info("POST (for item delete) /{}", id);
        itemService.delete(id);
        return "redirect:/";
    }

    @PostMapping(value = "{itemId}/comment")
    public String createComment(@ModelAttribute CommentDtoReq dtoReq,
                                @PathVariable("itemId") Integer itemId) {
        log.info("POST (for comment create) /{}/comment, dtoReq={}", itemId, dtoReq);
        commentService.create(commentMapper.dtoReq2Model(dtoReq));
        return "redirect:" + itemId;
    }

    @PostMapping(value = "{itemId}/comment/{id}")
    public String updateComment(@ModelAttribute CommentDtoReq dtoReq,
                                @PathVariable("itemId") Integer itemId,
                                @PathVariable("id") Integer id) {
        log.info("POST (for comment update) /{}/comment/{}, dtoReq={}", itemId, id, dtoReq);
        commentService.update(commentMapper.dtoReq2Model(dtoReq));
        return "redirect:" + itemId;
    }

    @PostMapping(value = "{itemId}/comment/{id}", params = "_method=delete")
    public String deleteComment(@PathVariable("itemId") Integer itemId,
                                @PathVariable("id") Integer id) {
        log.info("POST (for comment delete) /{}/comment/{}", itemId, id);
        commentService.delete(id);
        return "redirect:" + itemId;
    }
}