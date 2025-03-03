package ru.noir74.blog.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.models.comment.CommentEntity;
import ru.noir74.blog.repositories.intf.CommentRepository;

import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<CommentEntity> findById(Integer id) {
        var row = jdbcTemplate.queryForRowSet("SELECT id, message, post_id FROM comments WHERE id = ?", id);
        return row.next() ?
                Optional.of(CommentEntity.builder()
                        .id(id)
                        .message(row.getString("message"))
                        .postId(row.getInt("post_id"))
                        .build())
                : Optional.empty();
    }

    @Override
    public List<CommentEntity> findAllByPostId(Integer postId) {
        return new LinkedList<>(jdbcTemplate.query("SELECT id, message, post_id FROM comments WHERE post_id = ? ORDER BY changed DESC",
                (rs, rowNum) -> CommentEntity.builder()
                        .id(rs.getInt("id"))
                        .message(rs.getString("message"))
                        .postId(rs.getInt("post_id"))
                        .build()
                , postId));
    }

    @Override
    @Transactional
    public Integer save(CommentEntity commentEntity) {
        return Objects.isNull(commentEntity.getId()) ? insert(commentEntity) : update(commentEntity);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM comments WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) cnt FROM comments WHERE id = ?";
        return !Objects.equals(jdbcTemplate.queryForObject(sql, Integer.class, id), (Integer) 0);
    }

    @Transactional
    private Integer insert(CommentEntity commentEntity) {
        String sql = "INSERT INTO comments (message, post_id, changed) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, commentEntity.getMessage());
            stmt.setInt(2, commentEntity.getPostId());
            stmt.setTimestamp(3, commentEntity.getChanged());
            return stmt;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Transactional
    private Integer update(CommentEntity commentEntity) {
        String sql = "UPDATE comments SET message = ?, changed = ? WHERE id = ?";

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, commentEntity.getMessage());
            stmt.setTimestamp(2, commentEntity.getChanged());
            stmt.setInt(3, commentEntity.getId());
            return stmt;
        });
        return commentEntity.getId();
    }
}
