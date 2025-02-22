package ru.noir74.blog.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.noir74.blog.models.comment.Comment;
import ru.noir74.blog.models.comment.CommentDtoReq;
import ru.noir74.blog.models.comment.CommentDtoResp;
import ru.noir74.blog.models.comment.CommentEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper modelMapper;

    public Comment dtoReq2Model(CommentDtoReq dtoReq) {
        return Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, Comment.class)).orElse(null);
    }

    public CommentDtoResp model2dtoResp(Comment model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, CommentDtoResp.class)).orElse(null);
    }

    public CommentEntity model2entity(Comment model) {
        var entity = Optional.ofNullable(model)
                .map(obj -> modelMapper.map(obj, CommentEntity.class))
                .orElse(null);
        Optional.ofNullable(entity).ifPresent(obj -> obj.setChanged(Timestamp.from(Instant.now())));
        return entity;
    }

    public Comment entity2Model(CommentEntity entity) {
        return Optional.ofNullable(entity)
                .map(obj -> modelMapper.map(obj, Comment.class))
                .orElse(null);
    }

    public List<Comment> BulkEntity2Model(List<CommentEntity> entities) {
        return entities.stream()
                .map(this::entity2Model)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<CommentEntity> BulkModel2Entity(List<Comment> models) {
        return models.stream()
                .map(this::model2entity)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<CommentDtoResp> BulkModel2dtoResp(List<Comment> models) {
        return models.stream()
                .map(this::model2dtoResp)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
