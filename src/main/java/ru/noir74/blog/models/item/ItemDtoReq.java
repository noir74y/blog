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
    private String title;
    private String message;
    private String newItemSelectedTagsCsv;
    private String newItemNewTagsCsv;

    public List<String> newItemTagNameList() {
        Stream<String> newItemSelectedStream = Arrays.stream(Optional.ofNullable(newItemSelectedTagsCsv).orElse("").split(","));
        Stream<String> newItemNewTagsStream = Arrays.stream(Optional.ofNullable(newItemNewTagsCsv).orElse("").split(","));
        return Stream.concat(newItemSelectedStream, newItemNewTagsStream)
                .filter(newItemTagName -> !newItemTagName.isBlank())
                .map(newItemTagName -> newItemTagName.replaceAll("^\\s*",""))
                .map(newItemTagName -> newItemTagName.replaceAll("\\s*$",""))
                .distinct()
                .toList();
    }
}
