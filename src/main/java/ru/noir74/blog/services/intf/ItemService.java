package ru.noir74.blog.services.intf;

import ru.noir74.blog.models.item.Item;
import ru.noir74.blog.models.item.ItemBrief;
import ru.noir74.blog.models.item.ItemImage;

import java.io.IOException;
import java.util.List;

public interface ItemService {
    List<ItemBrief> findPage(String page, String size, String selectedTags);

    Item findById(Integer id);

    void findImageById(ItemImage itemImage) throws IOException;

    Integer create(Item item) throws IOException;

    void update(Item item) throws IOException;

    void setImageById(ItemImage itemImage) throws IOException;

    void delete(Integer id);
}
