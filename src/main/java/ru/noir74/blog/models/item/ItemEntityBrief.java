package ru.noir74.blog.models.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntityBrief {
    private Integer id;
    private String title;
    private String message;
    private Integer likes;
    private Integer commentsCounter;
    private String tagsCSV;

    public static String sortTagsCSV(String unsortedTagsCSV) {
        return Arrays.stream(unsortedTagsCSV.split(",")).sorted().collect(Collectors.joining(","));
    }
}
