package ru.noir74.blog.mappers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.noir74.blog.models.post.PostImage;
import ru.noir74.blog.models.post.PostImageEntity;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostImageMapper {
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

        TypeMap<PostImage, PostImageEntity> model2entityPropertyMapper = modelMapper.createTypeMap(PostImage.class, PostImageEntity.class);

        model2entityPropertyMapper.addMappings(modelMapper ->
                modelMapper.using(multipartFile2imageBytesConverter).map(PostImage::getFile, PostImageEntity::setImage));

        model2entityPropertyMapper.addMappings(modelMapper ->
                modelMapper.using(multipartFile2imageNameConverter).map(PostImage::getFile, PostImageEntity::setImageName));
    }

    public PostImageEntity model2entity(PostImage model) {
        return Optional.ofNullable(model)
                .map(obj -> modelMapper.map(obj, PostImageEntity.class))
                .orElse(null);
    }
}
