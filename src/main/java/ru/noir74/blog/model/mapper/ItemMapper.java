package ru.noir74.blog.model.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.noir74.blog.model.domain.Item;
import ru.noir74.blog.model.dto.ItemDtoReq;
import ru.noir74.blog.model.dto.ItemDtoResp;
import ru.noir74.blog.model.entity.ItemEntity;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final ModelMapper modelMapper;

    public Item dtoReq2Model(ItemDtoReq dtoReq) {
        return Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, Item.class)).orElse(null);
    }

    public ItemDtoResp model2dtoResp(Item model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, ItemDtoResp.class)).orElse(null);
    }

    public ItemEntity model2entity(Item model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, ItemEntity.class)).orElse(null);
    }

    public Item entity2Model(ItemEntity entity) {
        return Optional.ofNullable(entity).map(obj -> modelMapper.map(entity, Item.class)).orElse(null);
    }
}
