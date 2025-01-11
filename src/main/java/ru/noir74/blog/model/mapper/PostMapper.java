package ru.noir74.blog.model.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.noir74.blog.model.domain.Post;
import ru.noir74.blog.model.dto.PostDtoReq;
import ru.noir74.blog.model.dto.PostDtoResp;
import ru.noir74.blog.model.entity.PostEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final ModelMapper modelMapper;

    public Post dtoReq2Model(PostDtoReq dtoReq) {
        return Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, Post.class)).orElse(null);
    }

    public PostDtoResp model2dtoResp(Post model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, PostDtoResp.class)).orElse(null);
    }

    public PostEntity model2entity(Post model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, PostEntity.class)).orElse(null);
    }

    public Post entity2Model(PostEntity entity) {
        return Optional.ofNullable(entity).map(obj -> modelMapper.map(entity, Post.class)).orElse(null);
    }
}
