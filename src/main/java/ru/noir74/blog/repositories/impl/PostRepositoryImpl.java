package ru.noir74.blog.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.models.post.PostEntity;
import ru.noir74.blog.models.post.PostEntityBrief;
import ru.noir74.blog.models.post.PostImageEntity;
import ru.noir74.blog.repositories.intf.CommentRepository;
import ru.noir74.blog.repositories.intf.PostRepository;
import ru.noir74.blog.repositories.intf.TagRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;

    @Override
    public List<PostEntityBrief> findByPage(Integer page, Integer size) {
        var sql = "WITH " +
                "t1 AS ( SELECT * FROM posts i ORDER BY changed DESC OFFSET ? LIMIT ? ), " +
                "t2 AS ( SELECT c.post_id, COUNT(c.post_id) commentsCounter " +
                "FROM comments c " +
                "WHERE c.post_id IN (SELECT id FROM t1) " +
                "GROUP BY c.post_id), " +
                "t3 AS ( SELECT it.post_id post_id, STRING_AGG(t.name,',') tagsCSV " +
                "FROM posts_tags it " +
                "JOIN t1 ON t1.id = it.post_id " +
                "JOIN tags t ON t.id = it.tag_id " +
                "GROUP BY it.post_id) " +
                "SELECT t1.id, t1.title, t1.message, t1.likes, t2.commentsCounter, t3.tagsCSV " +
                "FROM t1 " +
                "LEFT OUTER JOIN t2 ON t2.post_id = t1.id " +
                "LEFT OUTER JOIN t3 ON t3.post_id = t1.id " +
                "ORDER BY t1.changed DESC";

        return new LinkedList<>(jdbcTemplate.query(sql,
                (rs, rowNum) -> PostEntityBrief.builder()
                        .id(rs.getInt("id"))
                        .title(rs.getString("title"))
                        .message(rs.getString("message"))
                        .likes(rs.getInt("likes"))
                        .commentsCounter(rs.getInt("commentsCounter"))
                        .tagsCSV(PostEntityBrief.sortTagsCSV(rs.getString("tagsCSV"))).build()
                , (page - 1) * size, size));
    }

    @Override
    public Optional<PostEntity> findById(Integer id) {
        var row = jdbcTemplate.queryForRowSet("SELECT title, message, likes FROM posts WHERE id = ?", id);
        return row.next() ?
                Optional.of(PostEntity.builder()
                        .id(id)
                        .title(row.getString("title"))
                        .message(row.getString("message"))
                        .likes(row.getInt("likes"))
                        .build())
                : Optional.empty();
    }

    @Override
    public PostImageEntity findImageById(Integer id) {
        var sql = "SELECT id, image, image_name FROM posts WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new PostImageEntityMapper(), id);
    }

    @Override
    @Transactional
    public Integer save(PostEntity postEntity) {
        return Objects.isNull(postEntity.getId()) ? insert(postEntity) : update(postEntity);
    }

    @Override
    @Transactional
    public void saveImageById(PostImageEntity postImageEntity) {
        String sql = "UPDATE posts SET image = ?, image_name = ? WHERE id = ?";
        jdbcTemplate.update(sql, postImageEntity.getImage(), postImageEntity.getImageName(), postImageEntity.getId());
    }

    @Override
    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) cnt FROM posts WHERE id = ?";
        return !Objects.equals(jdbcTemplate.queryForObject(sql, Integer.class, id), (Integer) 0);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM posts WHERE id = ?", id);
    }

    @Override
    @Transactional
    public void addLike(Integer id) {
        String sql = "UPDATE posts SET likes = likes + 1 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Transactional
    private Integer insert(PostEntity postEntity) {
        String sql = "INSERT INTO posts (title, message, likes, changed) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, postEntity.getTitle());
            stmt.setString(2, postEntity.getMessage());
            stmt.setInt(3, postEntity.getLikes());
            stmt.setTimestamp(4, postEntity.getChanged());
            return stmt;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Transactional
    private Integer update(PostEntity postEntity) {
        String sql = "UPDATE posts SET title = ?,  message = ?, likes = ?, changed = ? WHERE id = ?";

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, postEntity.getTitle());
            stmt.setString(2, postEntity.getMessage());
            stmt.setInt(3, Optional.ofNullable(postEntity.getLikes()).orElse(0));
            stmt.setTimestamp(4, postEntity.getChanged());
            stmt.setInt(5, postEntity.getId());
            return stmt;
        });
        return postEntity.getId();
    }
}

class PostImageEntityMapper implements RowMapper<PostImageEntity> {

    @Override
    public PostImageEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return PostImageEntity.builder()
                .id(rs.getInt("id"))
                .image(rs.getBytes("image"))
                .imageName(rs.getString("image_name")).build();
    }
}
