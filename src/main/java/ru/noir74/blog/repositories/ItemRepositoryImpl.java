package ru.noir74.blog.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.models.item.ItemEntity;
import ru.noir74.blog.models.item.ItemEntityBrief;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
                "t1 AS ( SELECT * FROM blog.items i ORDER BY changed DESC OFFSET ? LIMIT ? ), " +
                "t2 AS ( SELECT c.item_id, COUNT(c.item_id) commentsCounter " +
                "FROM blog.comments c " +
                "WHERE c.item_id IN (SELECT id FROM t1) " +
                "GROUP BY c.item_id), " +
                "t3 AS ( SELECT it.item_id item_id, STRING_AGG(t.name,',') tagsCSV " +
                "FROM blog.items_tags it " +
                "JOIN t1 ON t1.id = it.item_id " +
                "JOIN blog.tags t ON t.id = it.tag_id " +
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
                        .tagsCSV(rs.getString("tagsCSV")).build()
                , (page - 1) * size, size));
    }

    @Override
    public Optional<ItemEntity> findById(Integer id) {
        var row = jdbcTemplate.queryForRowSet("SELECT title, message, likes FROM blog.items WHERE id = ?", id);
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
    @Transactional
    public Integer save(ItemEntity itemEntity) {
        return Objects.isNull(itemEntity.getId()) ? insert(itemEntity) : update(itemEntity);
    }

    @Override
    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) cnt FROM blog.items WHERE id = ?";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"cnt"});
            stmt.setInt(1, id);
            return stmt;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue() != 0;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        String sql = "DELETE FROM blog.items WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(0, id);
            return stmt;
        });
    }

    @Override
    @Transactional
    public void addLike(Integer id) {
        changeLikes(id, 1);
    }

    @Override
    @Transactional
    public void removeLike(Integer id) {
        changeLikes(id, -1);
    }

    @Transactional
    private void changeLikes(Integer id, int change) {
        String sql = "UPDATE blog.items SET likes = likes + ?, changed = ? WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, change);
            stmt.setTimestamp(2, Timestamp.from(LocalDateTime.now().toInstant((ZoneOffset.UTC))));
            stmt.setInt(3, id);
            return stmt;
        });
    }

    @Transactional
    private Integer insert(ItemEntity itemEntity) {
        String sql = "INSERT INTO blog.items (title, message, likes, changed) VALUES (?, ?, ?, ?)";
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
        String sql = "UPDATE blog.items SET title = ?,  message = ?, changed = ? WHERE id = ?";

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, itemEntity.getTitle());
            stmt.setString(2, itemEntity.getMessage());
            stmt.setTimestamp(3, itemEntity.getChanged());
            stmt.setInt(4, itemEntity.getId());
            return stmt;
        });
        return itemEntity.getId();
    }
}
