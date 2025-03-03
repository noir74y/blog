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

    @PostMapping(value = "{postId}/comment")
    public String create(@ModelAttribute CommentDtoReq dtoReq,
                         @PathVariable("postId") Integer postId) {
        log.info("POST (for comment create) /{}/comment, dtoReq={}", postId, dtoReq);
        commentService.create(commentMapper.dtoReq2Model(dtoReq));
        return "redirect:" + postId;
    }

    @PostMapping(value = "{postId}/comment/{id}")
    public String update(@ModelAttribute CommentDtoReq dtoReq,
                         @PathVariable("postId") Integer postId,
                         @PathVariable("id") Integer id) {
        log.info("POST (for comment update) /{}/comment/{}, dtoReq={}", postId, id, dtoReq);
        commentService.update(commentMapper.dtoReq2Model(dtoReq));
        return "redirect:" + postId;
    }

    @PostMapping(value = "{postId}/comment/{id}", params = "_method=delete")
    public String delete(@PathVariable("postId") Integer postId,
                         @PathVariable("id") Integer id) {
        log.info("POST (for comment delete) /{}/comment/{}", postId, id);
        commentService.delete(id);
        return "redirect:" + postId;
    }
}
