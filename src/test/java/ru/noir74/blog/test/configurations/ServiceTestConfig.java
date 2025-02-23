package ru.noir74.blog.test.configurations;

import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.repositories.TagRepository;
import ru.noir74.blog.services.TagService;
import ru.noir74.blog.services.TagServiceImpl;

@Configuration
@Import({MappersTestConfig.class,ModelsTestConfig.class})
public class ServiceTestConfig {
    @Bean
    public TagRepository tagRepository() {
        return Mockito.mock(TagRepository.class);
    }

    @Bean
    public TagMapper tagMapper(ModelMapper modelMapper) {
        return new TagMapper(modelMapper);
    }

    @Bean
    public TagService tagService(TagRepository tagRepository, TagMapper tagMapper) {
        return new TagServiceImpl(tagRepository, tagMapper);
    }
}
