package ru.noir74.blog.service;

import ru.noir74.blog.model.domain.Item;
import ru.noir74.blog.model.domain.ItemBrief;

import java.util.List;

public interface ItemService {
    List<ItemBrief> getPage(Integer page, Integer size);

    Item get(Integer id);

    Item create(Item item);

    void update(Item item);

    void delete(Integer id);

    void addLike(Integer id);

    void removeLike(Integer id);
}
