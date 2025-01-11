package ru.noir74.blog.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.noir74.blog.model.domain.Tag;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts", schema = "blog")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "post")
    private String post;
    @Column(name = "picture")
    private byte[] picture;
    @Column(name = "likes")
    private Integer likes;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @JoinColumn(name = "post_id")
    private Set<CommentEntity> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
}
