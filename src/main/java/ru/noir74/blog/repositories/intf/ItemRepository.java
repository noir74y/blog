package ru.noir74.blog.repositories.intf;

import ru.noir74.blog.models.item.ItemEntity;
import ru.noir74.blog.models.item.ItemEntityBrief;
import ru.noir74.blog.models.item.ItemImageEntity;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    List<ItemEntityBrief> findByPage(Integer page, Integer size);

    Optional<ItemEntity> findById(Integer id);

    ItemImageEntity findImageById(Integer id);

    Integer save(ItemEntity itemEntity);

    void saveImageById(ItemImageEntity itemImageEntity);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    void addLike(Integer id);
}
