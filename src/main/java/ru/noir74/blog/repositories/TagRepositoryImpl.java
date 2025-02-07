package ru.noir74.blog.repositories;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.models.tag.TagEntity;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;
    private List<TagEntity> allTagEntityList;

    @PostConstruct
    @Transactional(readOnly = true)
    private void populateAllTagEntityList() {
        this.allTagEntityList = new LinkedList<>(jdbcTemplate.query("SELECT id, name FROM blog.tags ORDER BY name",
                (rs, rowNum) -> TagEntity.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name")).build()));
    }

    @Override
    public List<TagEntity> findAll() {
        return this.allTagEntityList.stream().sorted().toList();
    }

    @Override
    public Optional<TagEntity> findById(Integer id) {
        return this.allTagEntityList.stream().filter(tagEntity -> tagEntity.getId().equals(id)).findAny();
    }

    @Override
    public Optional<TagEntity> findByName(String name) {
        return this.allTagEntityList.stream().filter(tagEntity -> tagEntity.getName().equals(name)).findAny();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagEntity> findAllByItemId(Integer itemId) {
        return new LinkedList<>(jdbcTemplate.query(
                        "SELECT tag_id FROM blog.items_tags item_id = ?",
                        (rs, rowNum) -> rs.getInt("tag_id"), itemId)
                .stream()
                .map(tag_id ->
                        allTagEntityList.stream()
                                .filter(obj -> obj.getId().equals(tag_id))
                                .findAny().orElse(null))
                .filter(Objects::nonNull).sorted().toList());
    }

    @Override
    @Transactional
    public TagEntity save(TagEntity tagEntity) {
        String sql = "INSERT INTO blog.tags (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, tagEntity.getName());
            return stmt;
        }, keyHolder);

        var newTagEntity = TagEntity.builder().id(Objects.requireNonNull(keyHolder.getKey()).intValue()).name(tagEntity.getName()).build();
        this.allTagEntityList.add(newTagEntity);
        return newTagEntity;
    }

    @Override
    public List<TagEntity> save(List<TagEntity> tagEntities) {
        return tagEntities.stream().map(this::save).toList();
    }

    @Override
    @Transactional
    public void unstickFromItem(Integer itemId) {
        jdbcTemplate.update("DELETE FROM blog.items_tags WHERE item_id = ?", itemId);
    }

    @Override
    @Transactional
    public void stickToItem(List<Integer> tagIdList, Integer itemId) {
        jdbcTemplate.batchUpdate("INSERT INTO blog.items_tags (item_id, tag_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, itemId);
                        ps.setInt(2, tagIdList.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return tagIdList.size();
                    }
                });
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM blog.tags WHERE id = ?", id);
        this.allTagEntityList = this.allTagEntityList.stream()
                .filter(tagEntity -> !tagEntity.getId().equals(id))
                .sorted().toList();
        this.populateAllTagEntityList();
    }

    @Override
    public boolean existsById(Integer id) {
        return allTagEntityList.stream().anyMatch(tag -> Objects.equals(tag.getId(), id));
    }

    @Override
    public boolean existsByName(String name) {
        return allTagEntityList.stream().anyMatch(tag -> Objects.equals(tag.getName(), name));
    }
}
