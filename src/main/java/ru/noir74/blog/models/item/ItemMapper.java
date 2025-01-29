package ru.noir74.blog.models.item;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final ModelMapper modelMapper;

    public Item dtoReq2Model(ItemDtoReq dtoReq) {
        return Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, Item.class)).orElse(null);
    }

    public ItemDtoResp model2dtoResp(Item model) {
        var dtoResp = Optional.ofNullable(model).map(obj -> modelMapper.map(obj, ItemDtoResp.class)).orElse(null);
        Optional.ofNullable(dtoResp).ifPresent(obj -> obj.setCommentsCounter(obj.getComments().size()));
        return dtoResp;
    }

    public ItemEntity model2entity(Item model) {
        return Optional.ofNullable(model)
                .map(obj -> modelMapper.map(obj, ItemEntity.class))
                .orElse(null);
    }

    public Item entity2Model(ItemEntity entity) {
        return null;
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
                                Arrays.stream(dtoRespBrief.getTagsCSV().split(",")).collect(Collectors.toSet()
                                        )));
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
