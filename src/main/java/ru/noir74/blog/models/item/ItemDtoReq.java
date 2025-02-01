package ru.noir74.blog.models.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoReq {
    private Integer id;
    private String title;
    private String message;
    private byte[] picture;
    private Integer likes;
}
