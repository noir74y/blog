package ru.noir74.blog.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.noir74.blog.model.domain.Post;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags", schema = "blog")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique=true)
    private Integer id;
    @Column(name = "tag", nullable = false)
    private String tag;

    @ManyToMany(mappedBy = "tags")
    Set<PostEntity> posts;
}
