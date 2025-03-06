package ru.noir74.blog.models.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostImageEntity {
    private Integer id;
    private byte[] image;
    private String imageName;
}
