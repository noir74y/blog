package ru.noir74.blog.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.models.comment.CommentDtoReq;
import ru.noir74.blog.services.intf.CommentService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping(value = "{itemId}/comment")
    public String create(@ModelAttribute CommentDtoReq dtoReq,
                         @PathVariable("itemId") Integer itemId) {
        log.info("POST (for comment create) /{}/comment, dtoReq={}", itemId, dtoReq);
        commentService.create(commentMapper.dtoReq2Model(dtoReq));
        return "redirect:" + itemId;
    }

    @PostMapping(value = "{itemId}/comment/{id}")
    public String update(@ModelAttribute CommentDtoReq dtoReq,
                         @PathVariable("itemId") Integer itemId,
                         @PathVariable("id") Integer id) {
        log.info("POST (for comment update) /{}/comment/{}, dtoReq={}", itemId, id, dtoReq);
        commentService.update(commentMapper.dtoReq2Model(dtoReq));
        return "redirect:" + itemId;
    }

    @PostMapping(value = "{itemId}/comment/{id}", params = "_method=delete")
    public String delete(@PathVariable("itemId") Integer itemId,
                         @PathVariable("id") Integer id) {
        log.info("POST (for comment delete) /{}/comment/{}", itemId, id);
        commentService.delete(id);
        return "redirect:" + itemId;
    }
}
