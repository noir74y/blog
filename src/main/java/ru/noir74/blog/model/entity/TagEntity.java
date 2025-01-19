package ru.noir74.blog.model.entity;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    private Integer id;
    private String tag;
    Set<ItemEntity> items;
}
