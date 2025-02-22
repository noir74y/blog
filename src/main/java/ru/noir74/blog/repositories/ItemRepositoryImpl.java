package ru.noir74.blog.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.models.item.ItemEntity;
import ru.noir74.blog.models.item.ItemEntityBrief;
import ru.noir74.blog.models.item.ItemImageEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;

    @Override
    public List<ItemEntityBrief> findByPage(Integer page, Integer size) {
        var sql = "WITH " +
                "t1 AS ( SELECT * FROM items i ORDER BY changed DESC OFFSET ? LIMIT ? ), " +
                "t2 AS ( SELECT c.item_id, COUNT(c.item_id) commentsCounter " +
                "FROM comments c " +
                "WHERE c.item_id IN (SELECT id FROM t1) " +
                "GROUP BY c.item_id), " +
                "t3 AS ( SELECT it.item_id item_id, STRING_AGG(t.name,',') tagsCSV " +
                "FROM items_tags it " +
                "JOIN t1 ON t1.id = it.item_id " +
                "JOIN tags t ON t.id = it.tag_id " +
                "GROUP BY it.item_id) " +
                "SELECT t1.id, t1.title, t1.message, t1.likes, t2.commentsCounter, t3.tagsCSV " +
                "FROM t1 " +
                "LEFT OUTER JOIN t2 ON t2.item_id = t1.id " +
                "LEFT OUTER JOIN t3 ON t3.item_id = t1.id " +
                "ORDER BY t1.changed DESC";

        return new LinkedList<>(jdbcTemplate.query(sql,
                (rs, rowNum) -> ItemEntityBrief.builder()
                        .id(rs.getInt("id"))
                        .title(rs.getString("title"))
                        .message(rs.getString("message"))
                        .likes(rs.getInt("likes"))
                        .commentsCounter(rs.getInt("commentsCounter"))
                        .tagsCSV(ItemEntityBrief.sortTagsCSV(rs.getString("tagsCSV"))).build()
                , (page - 1) * size, size));
    }

    @Override
    public Optional<ItemEntity> findById(Integer id) {
        var row = jdbcTemplate.queryForRowSet("SELECT title, message, likes FROM items WHERE id = ?", id);
        return row.next() ?
                Optional.of(ItemEntity.builder()
                        .id(id)
                        .title(row.getString("title"))
                        .message(row.getString("message"))
                        .likes(row.getInt("likes"))
                        .build())
                : Optional.empty();
    }

    @Override
    public ItemImageEntity findImageById(Integer id) {
        var sql = "SELECT id, image, image_name FROM items WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ItemImageEntityMapper(), id);
    }

    @Override
    @Transactional
    public Integer save(ItemEntity itemEntity) {
        return Objects.isNull(itemEntity.getId()) ? insert(itemEntity) : update(itemEntity);
    }

    @Override
    @Transactional
    public void saveImageById(ItemImageEntity itemImageEntity) {
        String sql = "UPDATE items SET image = ?, image_name = ? WHERE id = ?";
        jdbcTemplate.update(sql, itemImageEntity.getImage(), itemImageEntity.getImageName(), itemImageEntity.getId());
    }

    @Override
    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) cnt FROM items WHERE id = ?";
        return !Objects.equals(jdbcTemplate.queryForObject(sql, Integer.class, id), (Integer) 0);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM items WHERE id = ?", id);
    }

    @Transactional
    private Integer insert(ItemEntity itemEntity) {
        String sql = "INSERT INTO items (title, message, likes, changed) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, itemEntity.getTitle());
            stmt.setString(2, itemEntity.getMessage());
            stmt.setInt(3, itemEntity.getLikes());
            stmt.setTimestamp(4, itemEntity.getChanged());
            return stmt;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Transactional
    private Integer update(ItemEntity itemEntity) {
        String sql = "UPDATE items SET title = ?,  message = ?, likes = ?, changed = ? WHERE id = ?";

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, itemEntity.getTitle());
            stmt.setString(2, itemEntity.getMessage());
            stmt.setInt(3, itemEntity.getLikes());
            stmt.setTimestamp(4, itemEntity.getChanged());
            stmt.setInt(5, itemEntity.getId());
            return stmt;
        });
        return itemEntity.getId();
    }
}

class ItemImageEntityMapper implements RowMapper<ItemImageEntity> {

    @Override
    public ItemImageEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ItemImageEntity.builder()
                .id(rs.getInt("id"))
                .image(rs.getBytes("image"))
                .imageName(rs.getString("image_name")).build();
    }
}
