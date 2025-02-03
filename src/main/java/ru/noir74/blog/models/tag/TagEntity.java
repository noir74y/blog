package ru.noir74.blog.models.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity  implements Comparable<TagEntity>{
    private Integer id;
    private String name;

    @Override
    public int compareTo(TagEntity tagEntity) {
        return this.name.compareTo(tagEntity.name);
    }
}
