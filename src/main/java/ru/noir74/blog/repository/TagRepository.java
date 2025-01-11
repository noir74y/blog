package ru.noir74.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.noir74.blog.model.entity.TagEntity;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {
}
