package ru.noir74.blog.mappers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.noir74.blog.models.item.*;
import ru.noir74.blog.models.tag.Tag;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final ModelMapper modelMapper;
    private final TagMapper tagMapper;

    @PostConstruct
    private void setup() {
        Converter<List<String>, List<Tag>> tagStringCsv2ModelConverter = tagNameList ->
                tagNameList.getSource()
                        .stream()
                        .map(tagName -> tagMapper.getTags().getOrDefault(tagName, Tag.builder().name(tagName).build()))
                        .toList();

        TypeMap<ItemDtoReq, Item> dtoReq2ModelPropertyMapper = modelMapper.createTypeMap(ItemDtoReq.class, Item.class);
        dtoReq2ModelPropertyMapper.addMappings(modelMapper ->
                modelMapper.using(tagStringCsv2ModelConverter).map(ItemDtoReq::itemTagNameList, Item::setTags));
    }

    public Item dtoReq2Model(ItemDtoReq dtoReq) {
        return Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, Item.class)).orElse(null);
    }

    public ItemDtoResp model2dtoResp(Item model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, ItemDtoResp.class)).orElse(null);
    }

    public ItemEntity model2entity(Item model) {
        var entity = Optional.ofNullable(model)
                .map(obj -> modelMapper.map(obj, ItemEntity.class))
                .orElse(null);
        Optional.ofNullable(entity).ifPresent(obj -> obj.setChanged(Timestamp.from(Instant.now())));
        return entity;
    }

    public Item entity2Model(ItemEntity entity) {
        return Optional.ofNullable(entity)
                .map(obj -> modelMapper.map(obj, Item.class))
                .orElse(null);
    }

    public ItemBrief entityBrief2ModelBrief(ItemEntityBrief entity) {
        return Optional.ofNullable(entity)
                .map(obj -> modelMapper.map(entity, ItemBrief.class))
                .orElse(null);
    }

    public ItemDtoRespBrief modelBrief2dtoRespBrief(ItemBrief model) {
        ItemDtoRespBrief dtoRespBrief = Optional.ofNullable(model)
                .map(obj -> modelMapper.map(model, ItemDtoRespBrief.class))
                .orElse(null);
        Optional.ofNullable(dtoRespBrief)
                .ifPresent(obj -> obj.setTags(
                        Arrays.stream(Optional.ofNullable(dtoRespBrief.getTagsCSV())
                                        .orElse("")
                                        .split(","))
                                .collect(Collectors.toList())));
        return dtoRespBrief;
    }

    public List<ItemBrief> BulkEntityBrief2ModelBrief(List<ItemEntityBrief> entities) {
        return entities.stream()
                .map(this::entityBrief2ModelBrief)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<ItemDtoRespBrief> BulkModelBrief2dtoRespBrief(List<ItemBrief> models) {
        return models.stream()
                .map(this::modelBrief2dtoRespBrief)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
