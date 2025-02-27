package ru.noir74.blog.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.mappers.ItemImageMapper;
import ru.noir74.blog.mappers.ItemMapper;
import ru.noir74.blog.models.item.Item;
import ru.noir74.blog.models.item.ItemBrief;
import ru.noir74.blog.models.item.ItemImage;
import ru.noir74.blog.models.item.ItemImageEntity;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.repositories.intf.ItemRepository;
import ru.noir74.blog.services.intf.CommentService;
import ru.noir74.blog.services.intf.ItemService;
import ru.noir74.blog.services.intf.TagService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor

public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ItemImageMapper itemImageMapper;
    private final TagService tagService;
    private final CommentService commentService;

    @Override
    public List<ItemBrief> findPage(String page, String size, String selectedTags) {
        var selectedTagList = new ArrayList<>(new ArrayList<>(Arrays.asList(selectedTags.split(","))));
        return itemMapper.bulkEntityBrief2ModelBrief(itemRepository.findByPage(Integer.parseInt(page), Integer.parseInt(size)))
                .stream()
                .filter(item -> {
                    if (selectedTags.isEmpty()) return true;
                    else
                        for (String selectedTag : selectedTagList)
                            return Arrays.asList(Optional.ofNullable(item.getTagsCSV()).orElseGet(() -> "")
                                    .split(",")).contains(selectedTag);
                    return false;
                })
                .toList();
    }

    @Override
    public Item findById(Integer id) {
        var item = itemMapper.entity2Model(itemRepository.findById(id).orElseThrow(() -> new NotFoundException("item is not found", String.valueOf(id))));
        item.setTags(tagService.findAllByItemId(item.getId()));
        item.setComments(commentService.findAllByItemId(item.getId()));
        return item;
    }

    @Override
    public void findImageById(ItemImage itemImage) throws IOException {
        var itemImageEntity = itemRepository.findImageById(itemImage.getId());
        if (itemImageEntity.isImageReadyToBeSaved() && Objects.nonNull(itemImage.getResponse())) {
            itemImage.getResponse().setContentType("image/" + itemImageEntity.getImageType());
            itemImage.getResponse().getOutputStream().write(itemImageEntity.getImage());
        }
    }

    @Override
    @Transactional
    public Integer create(Item item) throws IOException {
        item.setLikes(0);
        item.setId(itemRepository.save(itemMapper.model2entity(item)));
        saveImage(item);
        saveTags(item);
        return item.getId();
    }

    @Override
    @Transactional
    public void update(Item item) throws IOException {
        throwIfNotFound(item.getId());
        itemRepository.save(itemMapper.model2entity(item));
        saveImage(item);
        saveTags(item);
    }

    @Override
    @Transactional
    public void setImageById(ItemImage itemImage) throws IOException {
        itemRepository.saveImageById(itemImageMapper.model2entity(itemImage));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        throwIfNotFound(id);
        itemRepository.deleteById(id);
    }

    @Transactional
    private void saveImage(Item item) throws IOException {
        Optional.ofNullable(item.getFile()).ifPresent(file -> {
            ItemImageEntity itemImageEntity = null;
            try {
                itemImageEntity = ItemImageEntity.builder()
                        .id(item.getId())
                        .image(file.getBytes())
                        .imageName(file.getOriginalFilename()).build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (itemImageEntity.isImageReadyToBeSaved())
                itemRepository.saveImageById(itemImageEntity);
        });
    }

    @Transactional
    private void saveTags(Item item) {
        var existingTags = item.getTags().stream().filter(tag -> Objects.nonNull(tag.getId())).toList();
        var newTags = tagService.save(item.getTags().stream().filter(tag -> Objects.isNull(tag.getId())).toList());
        var allSelectedTags = Stream.concat(existingTags.stream(), newTags.stream()).toList();

        item.setTags(allSelectedTags);
        tagService.attachTagsToItem(item.getTags().stream().map(Tag::getId).toList(), item.getId());
    }

    private void throwIfNotFound(Integer id) {
        if (!itemRepository.existsById(id)) throw new NotFoundException("item is not found", String.valueOf(id));
    }

}
