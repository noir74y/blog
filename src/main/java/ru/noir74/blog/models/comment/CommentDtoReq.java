package ru.noir74.blog.models.comment;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDtoReq {
    private Integer id;
    private String message;
    private Integer itemId;
}
