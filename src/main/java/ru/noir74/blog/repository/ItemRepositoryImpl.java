package ru.noir74.blog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.noir74.blog.model.entity.ItemEntity;
import ru.noir74.blog.model.entity.ItemEntityBrief;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<ItemEntityBrief> findByPage(Integer page, Integer size) {
        var sql = "WITH " +
                "t1 AS ( SELECT * FROM blog.items i ORDER BY created OFFSET ? LIMIT ? ), " +
                "t2 AS ( SELECT c.item_id, COUNT(c.item_id) commentsCounter " +
                "FROM blog.comments c " +
                "WHERE c.item_id IN (SELECT id FROM t1) " +
                "GROUP BY c.item_id), " +
                "t3 AS ( SELECT it.item_id item_id, STRING_AGG(t.tag,',') tagsCSV " +
                "FROM blog.items_tags it " +
                "JOIN t1 ON t1.id = it.item_id " +
                "JOIN blog.tags t ON t.id = it.tag_id " +
                "GROUP BY it.item_id) " +
                "SELECT t1.id, t1.title, t1.message, t1.likes, t2.commentsCounter, t3.tagsCSV " +
                "FROM t1 " +
                "JOIN t2 ON t2.item_id = t1.id " +
                "JOIN t3 ON t3.item_id = t1.id";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> ItemEntityBrief.builder()
                        .id(rs.getInt("id"))
                        .title(rs.getString("title"))
                        .message(rs.getString("message"))
                        .likes(rs.getInt("likes"))
                        .commentsCounter(rs.getInt("commentsCounter"))
                        .tagsCSV(rs.getString("tagsCSV")).build()
                , (page - 1) * size, size);
    }

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
