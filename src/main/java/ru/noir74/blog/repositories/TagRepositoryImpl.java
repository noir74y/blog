package ru.noir74.blog.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.noir74.blog.models.tag.TagEntity;

import java.util.LinkedList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TagEntity> getAll() {
        return new LinkedList<>(jdbcTemplate.query("SELECT id, tag FROM blog.tags ORDER BY tag",
                (rs, rowNum) -> TagEntity.builder()
                        .id(rs.getInt("id"))
                        .tag(rs.getString("tag")).build()));
    }
}
