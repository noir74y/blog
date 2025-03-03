package ru.noir74.blog.models.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDtoReq {
    private Integer id;
    private String title;
    private String message;
    private Integer likes;
    private String postSelectedTagsCsv;
    private String postNewTagsCsv;
    private MultipartFile file;

    public List<String> postTagNameList() {
        Stream<String> postSelectedStream = Arrays.stream(Optional.ofNullable(postSelectedTagsCsv).orElse("").split(","));
        Stream<String> postNewTagsStream = Arrays.stream(Optional.ofNullable(postNewTagsCsv).orElse("").split(","));
        return Stream.concat(postSelectedStream, postNewTagsStream)
                .filter(postTagName -> !postTagName.isBlank())
                .map(postTagName -> postTagName.replaceAll("^\\s*", ""))
                .map(postTagName -> postTagName.replaceAll("\\s*$", ""))
                .distinct()
                .toList();
    }
}
