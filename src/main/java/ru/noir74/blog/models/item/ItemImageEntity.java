package ru.noir74.blog.models.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemImageEntity {
    private Integer id;
    private byte[] image;
    private String imageName;
}
