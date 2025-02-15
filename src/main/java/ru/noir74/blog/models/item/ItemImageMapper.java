package ru.noir74.blog.models.item;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemImageMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    private void setup() {
        Converter<MultipartFile, byte[]> multipartFile2imageBytesConverter = multipartFile ->
        {
            try {
                return multipartFile.getSource().getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        Converter<MultipartFile, String> multipartFile2imageNameConverter = multipartFile ->
                multipartFile.getSource().getOriginalFilename();

        TypeMap<ItemImage, ItemImageEntity> model2entityPropertyMapper = modelMapper.createTypeMap(ItemImage.class, ItemImageEntity.class);

        model2entityPropertyMapper.addMappings(modelMapper ->
                modelMapper.using(multipartFile2imageBytesConverter).map(ItemImage::getFile, ItemImageEntity::setImage));

        model2entityPropertyMapper.addMappings(modelMapper ->
                modelMapper.using(multipartFile2imageNameConverter).map(ItemImage::getFile, ItemImageEntity::setImageName));
    }

    public ItemImageEntity model2entity(ItemImage model) {
        return Optional.ofNullable(model)
                .map(obj -> modelMapper.map(obj, ItemImageEntity.class))
                .orElse(null);
    }
}
