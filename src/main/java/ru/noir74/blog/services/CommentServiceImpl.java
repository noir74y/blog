package ru.noir74.blog.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.models.comment.Comment;
import ru.noir74.blog.models.comment.CommentMapper;
import ru.noir74.blog.repositories.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllByItemId(Integer itemId) {
        return commentMapper.BulkEntity2Model(commentRepository.findAllByItemId(itemId));
    }

    @Override
    @Transactional(readOnly = true)
    public Comment findById(Integer id) {
        throwIfNotFound(id);
        return commentMapper.entity2Model(commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("comment is not found, comment_id = " + id)));
    }

    @Override
    @Transactional
    public void create(Comment comment) {
        commentRepository.save(commentMapper.model2entity(comment));
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        throwIfNotFound(comment.getId());
        commentRepository.save(commentMapper.model2entity(comment));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        throwIfNotFound(id);
        commentRepository.deleteById(id);
    }

    private void throwIfNotFound(Integer id) {
        if (!commentRepository.existsById(id))
            throw new RuntimeException("comment is not found, comment_id = " + id);
    }
}
