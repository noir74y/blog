package ru.noir74.blog.services;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ru.noir74.blog.models.item.Item;
import ru.noir74.blog.models.item.ItemBrief;

import java.io.IOException;
import java.util.List;

public interface ItemService {
    List<ItemBrief> findPage(String page, String size, String selectedTags);

    Item findById(Integer id);

    void findImageById(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException;

    void create(Item item);

    void update(Item item);

    void updateImageById(Integer id, MultipartFile file);

    void delete(Integer id);
}
