package ru.noir74.blog.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.noir74.blog.models.comment.CommentEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<CommentEntity> findById(Integer id) {
        var row = jdbcTemplate.queryForRowSet("SELECT id, message FROM blog.comments WHERE id = ?", id);
        var comment = CommentEntity.builder()
                .id(row.getInt("id"))
                .message(row.getString("message"))
                .build();
        return Optional.ofNullable(comment);
    }

    @Override
    public List<CommentEntity> findAllByItemId(Integer itemId) {
        return new LinkedList<>(jdbcTemplate.query("SELECT id, message FROM blog.comments WHERE item_id = ? ORDER BY created DESC",
                (rs, rowNum) -> CommentEntity.builder()
                        .id(rs.getInt("id"))
                        .message(rs.getString("message"))
                        .build()
                , itemId));
    }

    @Override
    public CommentEntity save(CommentEntity commentEntity) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
    }

    @Override
    public boolean existsById(Integer id) {
        return true;
    }
}
