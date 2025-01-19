package ru.noir74.blog.repository;

import org.springframework.stereotype.Repository;
import ru.noir74.blog.model.entity.ItemEntity;

import java.util.Optional;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    //private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<ItemEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public ItemEntity save(ItemEntity itemEntity) {
        return null;
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void addLike(Integer id) {

    }

    @Override
    public void removeLike(Integer id) {

    }
}
