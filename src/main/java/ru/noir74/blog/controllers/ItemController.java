package ru.noir74.blog.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.noir74.blog.models.comment.CommentDtoReq;
import ru.noir74.blog.models.comment.CommentMapper;
import ru.noir74.blog.models.item.ItemDtoReq;
import ru.noir74.blog.models.item.ItemDtoRespBrief;
import ru.noir74.blog.models.item.ItemImage;
import ru.noir74.blog.models.item.ItemMapper;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagDtoResp;
import ru.noir74.blog.models.tag.TagMapper;
import ru.noir74.blog.services.CommentService;
import ru.noir74.blog.services.ItemService;
import ru.noir74.blog.services.TagService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    private final TagMapper tagMapper;
    private final CommentService commentService;
    private final CommentMapper commentMapper;

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
        model.addAttribute("allTags", tagMapper.BulkModel2dtoResp(tagService.findAll()).stream().map(TagDtoResp::getName).toList());
        model.addAttribute("comments", commentMapper.BulkModel2dtoResp(commentService.findAllByItemId(id)));

        return "/item";
    }

    @GetMapping("/{id}/downloadImage")
    public void getImage(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        log.info("GET /items/{}/image", id);
        itemService.findImageById(ItemImage.builder().id(id).response(response).build());
    }

    @GetMapping("/images/{imageName}")
    public void getImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        // Путь к папке с изображениями
        String imagePath = "D:\\YandexDisk\\mine\\IdeaProjects\\jm-sprint3\\src\\main\\resources\\images\\" + imageName;
        File imageFile = new File(imagePath);
        response.setContentType("image/jpeg");

        try (FileInputStream fis = new FileInputStream(imageFile);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buff = new byte[4096];
            int bytes;
            while ((bytes = fis.read(buff)) != -1) {
                outputStream.write(buff, 0, bytes);
            }
        }
    }

    @PostMapping
    public String create(@ModelAttribute ItemDtoReq dtoReq) {
        log.info("POST (for item create) /items, dtoReq={}", dtoReq.toString());
        itemService.create(itemMapper.dtoReq2Model(dtoReq));
        return "redirect:/items";
    }

    @PostMapping(value = "/{id}")
    public String update(@ModelAttribute ItemDtoReq dtoReq, @PathVariable("id") Integer id) {
        log.info("POST (for item update) /items/{}, dtoReq={}", id, dtoReq.toString());
        itemService.update(itemMapper.dtoReq2Model(dtoReq));
        return "redirect:/items";
    }

    @PostMapping("/{id}/uploadImage")
    public String setImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        log.info("POST /items/{}/uploadImage", id);
        itemService.setImageById(id, file);
        return "/item";
    }

    @PostMapping(value = "/{id}", params = "_method=delete")
    public String delete(@PathVariable("id") Integer id) {
        log.info("POST (for item delete) /items/{}", id);
        itemService.delete(id);
        return "redirect:/items";
    }

    @PostMapping(value = "/{itemId}/comment")
    public String create(@ModelAttribute CommentDtoReq dtoReq,
                         @PathVariable("itemId") Integer itemId) {
        log.info("POST (for comment create) /items/{}/comment, dtoReq={}", itemId, dtoReq);
        commentService.create(commentMapper.dtoReq2Model(dtoReq));
        return "redirect:/item";
    }

    @PostMapping(value = "/{itemId}/comment/{id}")
    public String update(@ModelAttribute CommentDtoReq dtoReq,
                         @PathVariable("itemId") Integer itemId,
                         @PathVariable("id") Integer id) {
        log.info("POST (for comment update) /items/{}/comment/{}, dtoReq={}", itemId, id, dtoReq);
        commentService.update(commentMapper.dtoReq2Model(dtoReq));
        return "redirect:/item";
    }

    @PostMapping(value = "/{itemId}/comment/{id}", params = "_method=delete")
    public String delete(@PathVariable("itemId") Integer itemId,
                         @PathVariable("id") Integer id) {
        log.info("POST (for comment delete) /items/{}/comment/{}", itemId, id);
        commentService.delete(id);
        return "redirect:/item";
    }
}