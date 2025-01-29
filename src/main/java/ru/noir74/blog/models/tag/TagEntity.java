package ru.noir74.blog.models.tag;

import lombok.*;
import ru.noir74.blog.models.item.ItemEntity;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    private Integer id;
    private String tag;
}
