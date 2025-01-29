package ru.noir74.blog.models.tag;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    private Integer id;
    private String name;
}
