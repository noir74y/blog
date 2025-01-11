package ru.noir74.blog.service;

import ru.noir74.blog.model.domain.Item;

public interface ItemService {
    Item get(Integer id);

    Item create(Item item);

    void update(Item item);

    void delete(Integer id);

    void addLike(Integer id);

    void removeLike(Integer id);
}
