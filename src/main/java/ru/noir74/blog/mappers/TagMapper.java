package ru.noir74.blog.mappers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagDtoResp;
import ru.noir74.blog.models.tag.TagEntity;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TagMapper {
    private final ModelMapper modelMapper;

    @Getter
    private Map<String, Tag> tags;

    public TagDtoResp model2dtoResp(Tag model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, TagDtoResp.class)).orElse(null);
    }

    public TagEntity model2entity(Tag model) {
        return Optional.ofNullable(model)
                .map(obj -> modelMapper.map(obj, TagEntity.class))
                .orElse(null);
    }

    public Tag entity2Model(TagEntity entity) {
        return Optional.ofNullable(entity)
                .map(obj -> modelMapper.map(obj, Tag.class))
                .orElse(null);
    }

    public List<Tag> BulkEntity2Model(List<TagEntity> entities) {
        return entities.stream()
                .map(this::entity2Model)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<TagDtoResp> BulkModel2dtoResp(List<Tag> models) {
        return models.stream()
                .map(this::model2dtoResp)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<TagEntity> BulkModel2Entity(List<Tag> models) {
        return models.stream()
                .map(this::model2entity)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void setTags(List<Tag> tags) {
        this.tags = new LinkedHashMap<>();
        for (Tag tag : tags) this.tags.put(tag.getName(), tag);
    }
}
