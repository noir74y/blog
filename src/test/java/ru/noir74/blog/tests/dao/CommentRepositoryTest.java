package ru.noir74.blog.tests.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.noir74.blog.generics.DaoTest;
import ru.noir74.blog.models.comment.CommentEntity;
import ru.noir74.blog.models.post.PostEntityBrief;
import ru.noir74.blog.repositories.intf.CommentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CommentRepositoryTest extends DaoTest {
    @Autowired
    private CommentRepository commentRepository;

    List<PostEntityBrief> allPosts;
    Integer postId;
    List<CommentEntity> comments;
    Integer commentId;
    String message;


    @BeforeEach
    void setUp() {
        message = "message";

        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.update("INSERT INTO posts (title,message) VALUES ('title',?)", message);
        allPosts = postRepository.findByPage(1, 10);
        postId = allPosts.getFirst().getId();
        jdbcTemplate.execute("DELETE FROM comments");
        commentId = commentRepository.save(CommentEntity.builder().message(message).postId(postId).build());
    }

    @Test
    void findById() {
        var comment = commentRepository.findById(commentId);
        assertEquals(comment.orElse(null), CommentEntity.builder().id(commentId).message(message).postId(postId).build());
    }

    @Test
    void findAllByPostId() {
        assertEquals(
                List.of(CommentEntity.builder().id(commentId).message(message).postId(postId).build()),
                commentRepository.findAllByPostId(postId));
    }

    @Test
    void update() {
        var messageUpdated = message + "Updated";
        var commentUpdated = CommentEntity.builder().id(commentId).message(messageUpdated).postId(postId).build();
        commentRepository.save(commentUpdated);
        assertEquals(
                commentUpdated,
                commentRepository.findById(commentId).orElse(null));
    }

    @Test
    void deleteById() {
        commentRepository.deleteById(commentId);
        assertFalse(commentRepository.existsById(commentId));
    }
}
