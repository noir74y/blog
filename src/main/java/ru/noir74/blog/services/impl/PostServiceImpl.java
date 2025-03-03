package ru.noir74.blog.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.mappers.PostImageMapper;
import ru.noir74.blog.mappers.PostMapper;
import ru.noir74.blog.models.post.Post;
import ru.noir74.blog.models.post.PostBrief;
import ru.noir74.blog.models.post.PostImage;
import ru.noir74.blog.models.post.PostImageEntity;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.repositories.intf.PostRepository;
import ru.noir74.blog.services.intf.CommentService;
import ru.noir74.blog.services.intf.PostService;
import ru.noir74.blog.services.intf.TagService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostImageMapper postImageMapper;
    private final TagService tagService;
    private final CommentService commentService;

    @Override
    public List<PostBrief> findPage(String page, String size, String selectedTags) {
        var selectedTagList = new ArrayList<>(new ArrayList<>(Arrays.asList(selectedTags.split(","))));
        return postMapper.bulkEntityBrief2ModelBrief(postRepository.findByPage(Integer.parseInt(page), Integer.parseInt(size)))
                .stream()
                .filter(post -> {
                    if (selectedTags.isEmpty()) return true;
                    else
                        for (String selectedTag : selectedTagList)
                            return Arrays.asList(Optional.ofNullable(post.getTagsCSV()).orElseGet(() -> "")
                                    .split(",")).contains(selectedTag);
                    return false;
                })
                .toList();
    }

    @Override
    public Post findById(Integer id) {
        var post = postMapper.entity2Model(postRepository.findById(id).orElseThrow(() -> new NotFoundException("post is not found", String.valueOf(id))));
        post.setTags(tagService.findAllByPostId(post.getId()));
        post.setComments(commentService.findAllByPostId(post.getId()));
        return post;
    }

    @Override
    public void findImageById(PostImage postImage) throws IOException {
        var postImageEntity = postRepository.findImageById(postImage.getId());
        if (postImageEntity.isImageReadyToBeSaved() && Objects.nonNull(postImage.getResponse())) {
            postImage.getResponse().setContentType("image/" + postImageEntity.getImageType());
            postImage.getResponse().getOutputStream().write(postImageEntity.getImage());
        }
    }

    @Override
    @Transactional
    public Integer create(Post post) throws IOException {
        post.setLikes(0);
        post.setId(postRepository.save(postMapper.model2entity(post)));
        saveImage(post);
        saveTags(post);
        return post.getId();
    }

    @Override
    @Transactional
    public void update(Post post) throws IOException {
        throwIfNotFound(post.getId());
        postRepository.save(postMapper.model2entity(post));
        saveImage(post);
        saveTags(post);
    }

    @Override
    @Transactional
    public void setImageById(PostImage postImage) throws IOException {
        postRepository.saveImageById(postImageMapper.model2entity(postImage));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        throwIfNotFound(id);
        postRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addLike(Integer id) {
        throwIfNotFound(id);
        postRepository.addLike(id);
    }

    @Transactional
    private void saveImage(Post post) throws IOException {
        Optional.ofNullable(post.getFile()).ifPresent(file -> {
            PostImageEntity postImageEntity = null;
            try {
                postImageEntity = PostImageEntity.builder()
                        .id(post.getId())
                        .image(file.getBytes())
                        .imageName(file.getOriginalFilename()).build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (postImageEntity.isImageReadyToBeSaved())
                postRepository.saveImageById(postImageEntity);
        });
    }

    @Transactional
    private void saveTags(Post post) {
        var tagsReceived = Optional.ofNullable(post.getTags()).orElseGet(List::of);
        var existingTags = tagsReceived.stream().filter(tag -> Objects.nonNull(tag.getId())).toList();
        var newTags = tagService.save(tagsReceived.stream().filter(tag -> Objects.isNull(tag.getId())).toList());
        var allSelectedTags = Stream.concat(existingTags.stream(), newTags.stream()).toList();

        post.setTags(allSelectedTags);
        var tagsToStick = Optional.ofNullable(post.getTags()).orElseGet(List::of);
        tagService.attachTagsToPost(tagsToStick.stream().map(Tag::getId).toList(), post.getId());
    }

    private void throwIfNotFound(Integer id) {
        if (!postRepository.existsById(id)) throw new NotFoundException("post is not found", String.valueOf(id));
    }

}
