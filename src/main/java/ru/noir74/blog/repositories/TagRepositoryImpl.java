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
    private List<TagEntity> allTagsEntityList;

    @PostConstruct
    private void initializeAllTagsEntityList() {
        this.allTagsEntityList = new LinkedList<>(jdbcTemplate.query("SELECT id, name FROM blog.tags ORDER BY name",
                (rs, rowNum) -> TagEntity.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name")).build()));
    }

    @Override
    public List<TagEntity> findAll() {
        return this.allTagsEntityList;
    }

    @Override
    public Optional<TagEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<TagEntity> findAllByItemId(Integer itemId) {
        return new LinkedList<>(jdbcTemplate.query(
                        "SELECT tag_id FROM blog.items_tags item_id = ?",
                        (rs, rowNum) -> rs.getInt("tag_id"), itemId)
                .stream()
                .map(tag_id ->
                        allTagsEntityList
                                .stream()
                                .filter(obj -> obj.getId().equals(tag_id))
                                .findAny()
                                .orElse(null))
                .filter(Objects::nonNull)
                .sorted()
                .toList());

//        return new LinkedList<>(jdbcTemplate.query(
//                "SELECT t.id, t.name FROM blog.tags t JOIN blog.items_tags it ON it.tag_id = t.id AND it.item_id = ? ORDER BY t.name",
//                (rs, rowNum) -> TagEntity.builder()
//                        .id(rs.getInt("id"))
//                        .name(rs.getString("name")).build(), itemId));
    }

    @Override
    public TagEntity save(TagEntity tagEntity) {
        String sql = "INSERT INTO blogs.items (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, tagEntity.getName());
            return stmt;
        }, keyHolder);

        tagEntity.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());

        this.initializeAllTagsEntityList();
        return tagEntity;
    }

    @Override
    public void deleteById(Integer id) {
        this.initializeAllTagsEntityList();
    }

    @Override
    public boolean existsById(Integer id) {
        return allTagsEntityList.stream().anyMatch(tag -> Objects.equals(tag.getId(), id));
    }

    @Override
    public boolean existsByName(String name) {
        return allTagsEntityList.stream().anyMatch(tag -> Objects.equals(tag.getName(), name));
    }
}
