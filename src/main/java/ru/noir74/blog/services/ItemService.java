package ru.noir74.blog.services;

import ru.noir74.blog.models.item.Item;
import ru.noir74.blog.models.item.ItemBrief;

import java.util.List;

public interface ItemService {
    List<ItemBrief> findPage(String page, String size, String selectedTags);

    Item findById(Integer id);

    void create(Item item);

    void update(Item item);

    void delete(Integer id);
}
