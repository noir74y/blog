package ru.noir74.blog.models.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntityBrief {
    private Integer id;
    private String title;
    private String message;
    private Integer likes;
    private Integer commentsCounter;
    private String tagsCSV;

    public static String sortTagsCSV(String unsortedTagsCSV) {
        return Arrays.stream(Optional.ofNullable(unsortedTagsCSV).orElse("").split(","))
                .sorted()
                .collect(Collectors.joining(","));
    }
}
