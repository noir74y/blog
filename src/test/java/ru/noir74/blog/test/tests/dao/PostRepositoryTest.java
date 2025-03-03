package ru.noir74.blog.test.tests.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.noir74.blog.models.comment.CommentEntity;
import ru.noir74.blog.models.post.PostEntity;
import ru.noir74.blog.models.post.PostEntityBrief;
import ru.noir74.blog.models.post.PostImageEntity;
import ru.noir74.blog.repositories.intf.CommentRepository;
import ru.noir74.blog.test.generics.GenericDaoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PostRepositoryTest extends GenericDaoTest {
    @Autowired
    private CommentRepository commentRepository;

    List<PostEntityBrief> allPosts;
    Integer postId;
    List<CommentEntity> comments;
    Integer commentId;
    String title;
    String message;

    @BeforeEach
    void setUp() {
        title = "title";
        message = "message";
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.update("INSERT INTO posts (title,message) VALUES (?,?)", title, message);
        allPosts = postRepository.findByPage(1, 10);
        postId = allPosts.getFirst().getId();
    }

    @Test
    void findById() {
        var post = postRepository.findById(postId);
        assertEquals(post.orElse(null), PostEntity.builder().id(postId).title(title).likes(0).message(message).build());
    }

    @Test
    void insert() {
        var newPostToCreate = PostEntity.builder().title(title).message(message).likes(0).build();
        var newPostId = postRepository.save(newPostToCreate);
        var newPostCreated = postRepository.findById(newPostId).orElse(null);
        newPostToCreate.setId(newPostId);
        assertEquals(newPostToCreate, newPostCreated);
    }


    @Test
    void update() {
        var postToUpdate = postRepository.findById(postId).orElse(PostEntity.builder().build());
        var messageUpdated = postToUpdate.getMessage() + "Updated";
        postToUpdate.setMessage(messageUpdated);
        postRepository.save(postToUpdate);
        var postUpdated = postRepository.findById(postId).orElse(PostEntity.builder().build());
        assertEquals(messageUpdated, postUpdated.getMessage());
    }


    @Test
    void deleteById() {
        postRepository.deleteById(postId);
        assertFalse(postRepository.existsById(postId));
    }

    @Test
    void testImage() {
        var image = new byte[]{(byte) 0x00};
        var postImageEntityToSave = PostImageEntity.builder().id(postId).imageName("imageName").image(image).build();
        postRepository.saveImageById(postImageEntityToSave);
        var postImageEntitySaved = postRepository.findImageById(postId);
        assertEquals(postImageEntityToSave, postImageEntitySaved);
    }
}
