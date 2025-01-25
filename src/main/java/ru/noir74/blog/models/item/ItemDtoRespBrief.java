package ru.noir74.blog.models.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

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

    public List<String> getTags() {
        return Arrays.stream(tagsCSV.split(",")).toList();
    }
}
