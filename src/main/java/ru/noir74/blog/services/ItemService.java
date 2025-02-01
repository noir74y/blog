package ru.noir74.blog.services;

import ru.noir74.blog.models.item.Item;
import ru.noir74.blog.models.item.ItemBrief;

import java.util.List;

public interface ItemService {
    List<ItemBrief> getPage(String page, String size, String selectedTags);

    Item get(Integer id);

    Item create(Item item);

    void update(Item item);

    void delete(Integer id);

    void addLike(Integer id);

    void removeLike(Integer id);
}
