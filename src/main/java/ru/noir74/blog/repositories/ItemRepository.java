package ru.noir74.blog.repositories;


import ru.noir74.blog.models.item.ItemEntity;
import ru.noir74.blog.models.item.ItemEntityBrief;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    List<ItemEntityBrief> findByPage(Integer page, Integer size);

    Optional<ItemEntity> findById(Integer id);

    void save(ItemEntity itemEntity);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    //@Query("UPDATE ItemEntity SET likes = likes + 1 WHERE id = ?1")
    void addLike(Integer id);

    //@Query("UPDATE ItemEntity SET likes = likes - 1 WHERE id = ?1")
    void removeLike(Integer id);
}
