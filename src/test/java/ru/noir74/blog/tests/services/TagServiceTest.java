package ru.noir74.blog.tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.noir74.blog.generics.ServiceTest;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagEntity;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TagServiceTest extends ServiceTest {
    private List<Tag> tags;
    private List<TagEntity> tagEntities;

    @BeforeEach
    void setUp() {
        Tag tag = Tag.builder().id(0).name("tag0").build();
        tags = new LinkedList<>(List.of(tag));

        var tagEntity = tagMapper.model2entity(tag);
        tagEntities = new LinkedList<>(List.of(tagEntity));

        when(tagRepository.findAll()).thenReturn(tagEntities);
        tagService.populateTags();
    }

    @Test
    void testFindAll() {
        assertEquals(tags, tagService.findAll());
    }

    @Test
    void testFindById() {
        assertEquals(tags.getFirst(), tagService.findById(0));
    }

    @Test
    void testFindAllByPostId() {
        when(tagRepository.findAllByPostId(0)).thenReturn(tagEntities);
        assertEquals(tags, tagService.findAllByPostId(0));
        verify(tagRepository, times(1)).findAllByPostId(0);
    }

    @Test
    void testSave() {
        var newEntitiesToBeSaved = new LinkedList<>(List.of(TagEntity.builder().name("tag1").build()));
        var newEntitiesSaved = new LinkedList<>(List.of(TagEntity.builder().id(1).name("tag1").build()));

        var newModelsToBeSaved = tagMapper.bulkEntity2Model(newEntitiesToBeSaved);
        var newModelsSaved = tagMapper.bulkEntity2Model(newEntitiesSaved);

        when(tagRepository.save(newEntitiesToBeSaved)).thenReturn(newEntitiesSaved);
        assertEquals(newModelsSaved, tagService.save(newModelsToBeSaved));
        verify(tagRepository, times(1)).save(newEntitiesToBeSaved);
    }

    @Test
    void testAttachTagsToPost() {
        // just stub
        doNothing().when(tagRepository).unstickFromPost(0);
        doNothing().when(tagRepository).stickToPost(List.of(0), 0);
        tagService.attachTagsToPost(List.of(0), 0);
        verify(tagRepository, times(1)).unstickFromPost(0);
        verify(tagRepository, times(1)).stickToPost(List.of(0), 0);
    }
}
