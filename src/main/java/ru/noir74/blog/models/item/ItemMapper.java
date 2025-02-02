package ru.noir74.blog.models.item;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.noir74.blog.models.comment.Comment;
import ru.noir74.blog.models.comment.CommentEntity;
import ru.noir74.blog.models.comment.CommentMapper;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagEntity;
import ru.noir74.blog.models.tag.TagMapper;
import ru.noir74.blog.repositories.TagRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final ModelMapper modelMapper;
    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    @PostConstruct
    private void setup() {
        Converter<List<Comment>, List<CommentEntity>> commentModels2EntitiesConverter =
                models -> commentMapper.BulkModel2Entity(models.getSource());
        Converter<List<CommentEntity>, List<Comment>> commentEntities2ModelsConverter =
                entities -> commentMapper.BulkEntity2Model(entities.getSource());
        Converter<List<Tag>, List<TagEntity>> tagModels2EntitiesConverter =
                models -> tagMapper.BulkModel2Entity(models.getSource());
        Converter<List<TagEntity>, List<Tag>> tagEntities2ModelsConverter =
                entities -> tagMapper.BulkEntity2Model(entities.getSource());

        TypeMap<Item, ItemEntity> model2EntityPropertyMapper = modelMapper.createTypeMap(Item.class, ItemEntity.class);
        TypeMap<ItemEntity, Item> entity2ModelPropertyMapper = modelMapper.createTypeMap(ItemEntity.class, Item.class);

        model2EntityPropertyMapper.addMappings(modelMapper ->
                modelMapper.using(commentModels2EntitiesConverter).map(Item::getComments, ItemEntity::setComments));
        model2EntityPropertyMapper.addMappings(modelMapper ->
                modelMapper.using(tagModels2EntitiesConverter).map(Item::getTags, ItemEntity::setTags));

        entity2ModelPropertyMapper.addMappings(modelMapper ->
                modelMapper.using(commentEntities2ModelsConverter).map(ItemEntity::getComments, Item::setComments));
        entity2ModelPropertyMapper.addMappings(modelMapper ->
                modelMapper.using(tagEntities2ModelsConverter).map(ItemEntity::getTags, Item::setTags));
    }

    public Item dtoReq2Model(ItemDtoReq dtoReq) {
        var model = Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, Item.class)).orElse(null);
        var allTags = tagRepository.findAll();
        return model;
    }

    public ItemDtoResp model2dtoResp(Item model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, ItemDtoResp.class)).orElse(null);
    }

    public ItemEntity model2entity(Item model) {
        return Optional.ofNullable(model)
                .map(obj -> modelMapper.map(obj, ItemEntity.class))
                .orElse(null);
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
                        Arrays.stream(dtoRespBrief.getTagsCSV().split(",")).collect(Collectors.toList()
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
