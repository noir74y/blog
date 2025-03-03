package ru.noir74.blog.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.mappers.PostMapper;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.models.post.PostDtoReq;
import ru.noir74.blog.models.post.PostDtoRespBrief;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagDtoResp;
import ru.noir74.blog.services.intf.CommentService;
import ru.noir74.blog.services.intf.PostService;
import ru.noir74.blog.services.intf.TagService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostMapper postMapper;
    private final PostService postService;
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

        List<PostDtoRespBrief> posts = postMapper.bulkModelBrief2DtoRespBrief(postService.findPage(page, size, selectedTags));

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("posts", posts);
        model.addAttribute("selectedTags", new ArrayList<>(Arrays.stream(selectedTags.split(",")).toList()));
        model.addAttribute("allTags", tagService.findAll().stream().map(Tag::getName).toList());

        return "posts";
    }

    @GetMapping("{id}")
    public String get(Model model, @PathVariable("id") Integer id) {
        log.info("GET /{}", id);
        var postDtoResp = postMapper.model2dtoResp(postService.findById(id));

        model.addAttribute("id", postDtoResp.getId());
        model.addAttribute("title", postDtoResp.getTitle());
        model.addAttribute("message", postDtoResp.getMessage());
        model.addAttribute("likes", postDtoResp.getLikes());
        model.addAttribute("postSelectedTags", postDtoResp.getTags().stream().map(Tag::getName).toList());
        model.addAttribute("allTags", tagMapper.bulkModel2DtoResp(tagService.findAll()).stream().map(TagDtoResp::getName).toList());
        model.addAttribute("comments", commentMapper.bulkModel2DtoResp(commentService.findAllByPostId(id)));

        return "post";
    }

    @PostMapping(produces = "text/plain;charset=UTF-8")
    public String create(@ModelAttribute PostDtoReq dtoReq) throws IOException {
        log.info("POST (for post create) /, dtoReq={}", dtoReq.toString());
        postService.create(postMapper.dtoReq2Model(dtoReq));
        return "redirect:/";
    }

    @PostMapping(value = "{id}")
    public String update(@ModelAttribute PostDtoReq dtoReq, @PathVariable("id") Integer id) throws IOException {
        log.info("POST (for post update) /{}, dtoReq={}", id, dtoReq.toString());
        postService.update(postMapper.dtoReq2Model(dtoReq));
        return "redirect:" + dtoReq.getId();
    }

    @PostMapping(value = "{id}", params = "_method=delete")
    public String delete(@PathVariable("id") Integer id) {
        log.info("POST (for post delete) /{}", id);
        postService.delete(id);
        return "redirect:/";
    }

    @PostMapping(value = "{id}", params = "_action=back")
    public String back() {
        log.info("POST (back to posts\\'s list) ");
        return "redirect:/";
    }
}