package ru.noir74.blog.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.noir74.blog.models.post.PostImage;
import ru.noir74.blog.models.post.PostImageEntity;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostImageMapper {
    private final ModelMapper modelMapper;

    public PostImageEntity model2entity(PostImage model) {
        return Optional.ofNullable(model)
                .map(obj -> modelMapper.map(obj, PostImageEntity.class))
                .orElse(null);
    }

    public PostImage entity2model(PostImageEntity entity) {
        return Optional.ofNullable(entity)
                .map(obj -> modelMapper.map(obj, PostImage.class))
                .orElse(null);
    }
}
