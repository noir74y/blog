package ru.noir74.blog.models.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoReq {
    private Integer id;
    private String title;
    private String message;
    private Integer likes;
    private String itemSelectedTagsCsv;
    private String itemNewTagsCsv;

    public List<String> itemTagNameList() {
        Stream<String> itemSelectedStream = Arrays.stream(Optional.ofNullable(itemSelectedTagsCsv).orElse("").split(","));
        Stream<String> itemNewTagsStream = Arrays.stream(Optional.ofNullable(itemNewTagsCsv).orElse("").split(","));
        return Stream.concat(itemSelectedStream, itemNewTagsStream)
                .filter(itemTagName -> !itemTagName.isBlank())
                .map(itemTagName -> itemTagName.replaceAll("^\\s*", ""))
                .map(itemTagName -> itemTagName.replaceAll("\\s*$", ""))
                .distinct()
                .toList();
    }
}
