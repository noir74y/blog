package ru.noir74.blog.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.models.item.Item;
import ru.noir74.blog.models.item.ItemBrief;
import ru.noir74.blog.models.item.ItemMapper;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.repositories.ItemRepository;
import ru.noir74.blog.repositories.TagRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final TagService tagService;

    @Override
    @Transactional(readOnly = true)
    public List<ItemBrief> getPage(String page, String size, String selectedTags) {
        var selectedTagList = new ArrayList<>(new ArrayList<>(Arrays.asList(selectedTags.split(","))));
        return itemMapper.BulkEntityBrief2ModelBrief(itemRepository.findByPage(Integer.parseInt(page), Integer.parseInt(size)))
                .stream()
                .filter(obj -> {
                    if (selectedTags.isEmpty()) return true;
                    else
                        for (String tag : selectedTagList)
                            if (Optional.ofNullable(obj.getTagsCSV()).orElse("").matches(".*,?" + tag + ",?.*"))
                                return true;
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Item get(Integer id) {
        return itemMapper.entity2Model(itemRepository.findById(id).orElseThrow(() -> new NotFoundException("post is not found", "post_id = " + id)));
    }

    @Override
    @Transactional
    public void create(Item item) {
        item.setLikes(0);
        item.setCreated(LocalDateTime.now());
        itemRepository.save(itemMapper.model2entity(item));
        item.getTags().stream()
                .filter(tag -> Objects.isNull(tag.getId()))
                .forEach(tagService::save);
    }

    @Override
    @Transactional
    public void update(Item item) {
        throwIfNotFound(item.getId());
        itemRepository.save(itemMapper.model2entity(item));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        throwIfNotFound(id);
        itemRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addLike(Integer id) {
        throwIfNotFound(id);
        itemRepository.addLike(id);
    }

    @Override
    @Transactional
    public void removeLike(Integer id) {
        throwIfNotFound(id);
        itemRepository.removeLike(id);
    }

    private void throwIfNotFound(Integer id) {
        if (!itemRepository.existsById(id)) throw new NotFoundException("post is not found", "post_id = " + id);
    }

}
