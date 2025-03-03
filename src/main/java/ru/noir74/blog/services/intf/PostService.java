package ru.noir74.blog.services.intf;

import ru.noir74.blog.models.post.Post;
import ru.noir74.blog.models.post.PostBrief;
import ru.noir74.blog.models.post.PostImage;

import java.io.IOException;
import java.util.List;

public interface PostService {
    List<PostBrief> findPage(String page, String size, String selectedTags);

    Post findById(Integer id);

    void findImageById(PostImage postImage) throws IOException;

    Integer create(Post post) throws IOException;

    void update(Post post) throws IOException;

    void setImageById(PostImage postImage) throws IOException;

    void delete(Integer id);

    void addLike(Integer id);
}
