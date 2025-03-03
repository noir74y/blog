package ru.noir74.blog.models.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDtoRespBrief {
    private Integer id;
    private String title;
    private String message;
    private Integer likes;
    private Integer commentsCounter;
    private String tagsCSV;
    private List<String> tags;
}
