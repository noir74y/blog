package ru.noir74.blog.repositories;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.noir74.blog.models.tag.TagEntity;

import java.sql.PreparedStatement;
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
    private void populateAllTagEntityList() {
        this.allTagEntityList = new LinkedList<>(jdbcTemplate.query("SELECT id, name FROM blog.tags ORDER BY name",
                (rs, rowNum) -> TagEntity.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name")).build()));
    }

    @Override
    public List<TagEntity> findAll() {
        return this.allTagEntityList;
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
    public void save(TagEntity tagEntity) {
        String sql = "INSERT INTO blog.tags (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, tagEntity.getName());
            return stmt;
        }, keyHolder);

        this.allTagEntityList.add(TagEntity.builder().id(Objects.requireNonNull(keyHolder.getKey()).intValue()).name(tagEntity.getName()).build());
        this.allTagEntityList = this.allTagEntityList.stream().sorted().toList();
    }

    @Override
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
