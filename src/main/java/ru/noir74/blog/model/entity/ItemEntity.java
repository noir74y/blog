package ru.noir74.blog.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items", schema = "blog")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "message")
    private String message;
    @Column(name = "picture")
    private byte[] picture;
    @Column(name = "likes")
    private Integer likes;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @JoinColumn(name = "item_id")
    private Set<CommentEntity> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "items_tags", schema = "blog",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags;
}
