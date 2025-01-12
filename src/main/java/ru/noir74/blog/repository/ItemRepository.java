package ru.noir74.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.noir74.blog.model.entity.ItemEntity;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    @Modifying
    @Query("UPDATE ItemEntity SET likes = likes + 1 WHERE id = ?1")
    void addLike(Integer id);

    @Modifying
    @Query("UPDATE ItemEntity SET likes = likes - 1 WHERE id = ?1")
    void removeLike(Integer id);
}
