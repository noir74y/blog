package ru.noir74.blog.services;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ru.noir74.blog.models.item.Item;
import ru.noir74.blog.models.item.ItemBrief;
import ru.noir74.blog.models.item.ItemImage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ItemService {
    List<ItemBrief> findPage(String page, String size, String selectedTags);

    Item findById(Integer id);

    void findImageById(ItemImage itemImage) throws IOException;

    void create(Item item);

    void update(Item item);

    void setImageById(Integer id, MultipartFile file) throws IOException;

    void delete(Integer id);
}
