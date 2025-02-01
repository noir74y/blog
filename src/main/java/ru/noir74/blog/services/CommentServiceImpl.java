package ru.noir74.blog.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.models.comment.Comment;
import ru.noir74.blog.models.comment.CommentMapper;
import ru.noir74.blog.repositories.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<Comment> getAllByItemId(Integer itemId) {
        return commentMapper.BulkEntity2Model(commentRepository.findAllByItemId(itemId));
    }

    @Override
    public Comment get(Integer id) {
        throwIfNotFound(id);
        return commentMapper.entity2Model(commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("comment is not found", "comment_id = " + id)));
    }

    @Override
    public Comment create(Comment comment) {
        comment.setCreated(LocalDateTime.now());
        return commentMapper.entity2Model(commentRepository.save(commentMapper.model2entity(comment)));
    }

    @Override
    public void update(Comment comment) {
        throwIfNotFound(comment.getId());
        commentRepository.save(commentMapper.model2entity(comment));
    }

    @Override
    public void delete(Integer id) {
        throwIfNotFound(id);
        commentRepository.deleteById(id);
    }

    private void throwIfNotFound(Integer id) {
        if (!commentRepository.existsById(id)) throw new NotFoundException("comment is not found", "comment_id = " + id);
    }
}
