package ru.noir74.blog.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.models.item.Item;
import ru.noir74.blog.models.item.ItemBrief;
import ru.noir74.blog.models.item.ItemMapper;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.repositories.ItemRepository;

import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor

public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final TagService tagService;
    private final CommentService commentService;

    @Override
    public List<ItemBrief> findPage(String page, String size, String selectedTags) {
        var selectedTagList = new ArrayList<>(new ArrayList<>(Arrays.asList(selectedTags.split(","))));
        return itemMapper.BulkEntityBrief2ModelBrief(itemRepository.findByPage(Integer.parseInt(page), Integer.parseInt(size)))
                .stream()
                .filter(obj -> {
                    if (selectedTags.isEmpty()) return true;
                    else
                        for (String selectedTag : selectedTagList)
                            return Arrays.asList(Optional.ofNullable(obj.getTagsCSV()).orElseGet(() -> "")
                                    .split(",")).contains(selectedTag);
                    return false;
                })
                .toList();
    }

    @Override
    public Item findById(Integer id) {
        var item = itemMapper.entity2Model(itemRepository.findById(id).orElseThrow(() -> new RuntimeException("post is not found, post_id = " + id)));
        item.setTags(tagService.findAllByItemId(item.getId()));
        item.setComments(commentService.findAllByItemId(item.getId()));
        return item;
    }

    @Override
    @Transactional
    public void create(Item item) {
        item.setLikes(0);
        item.setId(itemRepository.save(itemMapper.model2entity(item)));
        saveTags(item);
    }

    @Override
    @Transactional
    public void update(Item item) {
        throwIfNotFound(item.getId());
        itemRepository.save(itemMapper.model2entity(item));
        saveTags(item);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        throwIfNotFound(id);
        itemRepository.deleteById(id);
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
        if (!itemRepository.existsById(id)) throw new RuntimeException("post is not found, post_id = " + id);
    }

}
