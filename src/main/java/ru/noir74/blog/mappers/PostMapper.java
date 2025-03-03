package ru.noir74.blog.mappers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.noir74.blog.models.post.*;
import ru.noir74.blog.models.tag.Tag;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final ModelMapper modelMapper;
    private final TagMapper tagMapper;

    @PostConstruct
    private void setup() {
        Converter<List<String>, List<Tag>> tagStringCsv2ModelConverter = tagNameList ->
                tagNameList.getSource()
                        .stream()
                        .map(tagName -> tagMapper.getName2TagMap().getOrDefault(tagName, Tag.builder().name(tagName).build()))
                        .toList();

        TypeMap<PostDtoReq, Post> dtoReq2ModelPropertyMapper = modelMapper.createTypeMap(PostDtoReq.class, Post.class);
        dtoReq2ModelPropertyMapper.addMappings(modelMapper ->
                modelMapper.using(tagStringCsv2ModelConverter).map(PostDtoReq::postTagNameList, Post::setTags));
    }

    public Post dtoReq2Model(PostDtoReq dtoReq) {
        return Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, Post.class)).orElse(null);
    }

    public PostDtoResp model2dtoResp(Post model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, PostDtoResp.class)).orElse(null);
    }

    public PostEntity model2entity(Post model) {
        var entity = Optional.ofNullable(model)
                .map(obj -> modelMapper.map(obj, PostEntity.class))
                .orElse(null);
        Optional.ofNullable(entity).ifPresent(obj ->
                obj.setChanged(Objects.nonNull(obj.getChanged()) ?
                        obj.getChanged() :
                        Timestamp.from(Instant.now())));
        return entity;
    }

    public Post entity2Model(PostEntity entity) {
        return Optional.ofNullable(entity)
                .map(obj -> modelMapper.map(obj, Post.class))
                .orElse(null);
    }

    public PostBrief entityBrief2ModelBrief(PostEntityBrief entity) {
        return Optional.ofNullable(entity)
                .map(obj -> modelMapper.map(entity, PostBrief.class))
                .orElse(null);
    }

    public PostDtoRespBrief modelBrief2dtoRespBrief(PostBrief model) {
        PostDtoRespBrief dtoRespBrief = Optional.ofNullable(model)
                .map(obj -> modelMapper.map(model, PostDtoRespBrief.class))
                .orElse(null);
        Optional.ofNullable(dtoRespBrief)
                .ifPresent(obj -> obj.setTags(
                        Arrays.stream(Optional.ofNullable(dtoRespBrief.getTagsCSV())
                                        .orElse("")
                                        .split(","))
                                .collect(Collectors.toList())));
        return dtoRespBrief;
    }

    public List<PostBrief> bulkEntityBrief2ModelBrief(List<PostEntityBrief> entities) {
        return entities.stream()
                .map(this::entityBrief2ModelBrief)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<PostDtoRespBrief> bulkModelBrief2DtoRespBrief(List<PostBrief> models) {
        return models.stream()
                .map(this::modelBrief2dtoRespBrief)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
