package ru.noir74.blog.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.noir74.blog.models.tag.TagEntity;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TagEntity> findAll() {
        return new LinkedList<>(jdbcTemplate.query("SELECT id, name FROM blog.tags ORDER BY name",
                (rs, rowNum) -> TagEntity.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name")).build()));
    }

    @Override
    public Optional<TagEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Set<TagEntity> findAllByItemId(Integer itemId) {
        return new LinkedHashSet<>(jdbcTemplate.query(
                "SELECT t.id, t.name FROM blog.tags t JOIN blog.items_tags it ON it.tag_id = t.id AND it.item_id = ? ORDER BY t.name",
                (rs, rowNum) -> TagEntity.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name")).build(), itemId));
    }

    @Override
    public TagEntity save(TagEntity tagEntity) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }
}
