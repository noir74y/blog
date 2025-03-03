package ru.noir74.blog.models.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostBrief {
    private Integer id;
    private String title;
    private String message;
    private Integer likes;
    private Integer commentsCounter;
    private String tagsCSV;
}
