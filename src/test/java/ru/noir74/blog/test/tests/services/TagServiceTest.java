package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagEntity;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TagServiceTest extends GenericServiceTest {
    private List<Tag> tags;
    private List<TagEntity> tagEntities;

    @BeforeEach
    void setUp() {
        Tag tag = Tag.builder().id(0).name("tag0").build();
        tags = new LinkedList<>(List.of(tag));

        var tagEntity = tagMapper.model2entity(tag);
        tagEntities = new LinkedList<>(List.of(tagEntity));

        when(tagRepository.findAll()).thenReturn(tagEntities);
        tagService.populateAllTags();
        verify(tagRepository, times(1)).findAll();
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
    void testFindAllByItemId() {
        when(tagRepository.findAllByItemId(0)).thenReturn(tagEntities);
        assertEquals(tags, tagService.findAllByItemId(0));
        verify(tagRepository, times(1)).findAllByItemId(0);
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
    void testAttachTagsToItem() {
        // just stub
        doNothing().when(tagRepository).unstickFromItem(0);
        doNothing().when(tagRepository).stickToItem(List.of(0), 0);
        tagService.attachTagsToItem(List.of(0), 0);
        verify(tagRepository, times(1)).unstickFromItem(0);
        verify(tagRepository, times(1)).stickToItem(List.of(0), 0);
    }
}
