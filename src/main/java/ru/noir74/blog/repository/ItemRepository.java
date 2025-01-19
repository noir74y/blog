package ru.noir74.blog.repository;


import ru.noir74.blog.model.domain.Item;
import ru.noir74.blog.model.entity.ItemEntity;

import java.util.Optional;

public interface ItemRepository {

    Optional<ItemEntity> findById(Integer id);

    ItemEntity save(ItemEntity itemEntity);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    //@Query("UPDATE ItemEntity SET likes = likes + 1 WHERE id = ?1")
    void addLike(Integer id);

    //@Query("UPDATE ItemEntity SET likes = likes - 1 WHERE id = ?1")
    void removeLike(Integer id);
}
