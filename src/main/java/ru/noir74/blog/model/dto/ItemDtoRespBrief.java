package ru.noir74.blog.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDtoRespBrief {
    private Integer id;
    private String preview;
    private Integer commentsCounter;
    private Integer likesCounter;
    private String tags;
}
