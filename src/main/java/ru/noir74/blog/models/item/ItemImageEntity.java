package ru.noir74.blog.models.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemImageEntity {
    private Integer id;
    private byte[] image;
    private String imageName;

    public boolean isImageReadyToBeSaved() {
        return Objects.nonNull(image) &&
                image.length != 0 &&
                Objects.nonNull(imageName) &&
                imageName.matches("^.+\\.\\w+$");
    }

    public String getImageType() {
        return Arrays.stream(imageName.split("\\."))
                .reduce((first, second) -> second)
                .orElse("");
    }
}
