package ru.noir74.blog.models.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoRespBrief {
    private Integer id;
    private String title;
    private String message;
    private Integer likes;
    private Integer commentsCounter;
    private String tagsCSV;
    private List<String> tags;
}
