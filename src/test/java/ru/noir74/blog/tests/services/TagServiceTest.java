package ru.noir74.blog.tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.noir74.blog.generics.GenericServiceTest;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagEntity;
import ru.noir74.blog.repositories.intf.TagRepository;
import ru.noir74.blog.services.intf.TagService;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TagServiceTest extends GenericServiceTest {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TagRepository tagRepositoryMock;
    @Autowired
    private TagService tagService;

    private List<Tag> tags;
    private List<TagEntity> tagEntities;

    @BeforeEach
    void setUp() {
        Tag tag = Tag.builder().id(0).name("tag0").build();
        tags = new LinkedList<>(List.of(tag));

        var tagEntity = tagMapper.model2entity(tag);
        tagEntities = new LinkedList<>(List.of(tagEntity));

        when(tagRepositoryMock.findAll()).thenReturn(tagEntities);
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
        when(tagRepositoryMock.findAllByPostId(0)).thenReturn(tagEntities);
        assertEquals(tags, tagService.findAllByPostId(0));
        verify(tagRepositoryMock, times(1)).findAllByPostId(0);
    }

    @Test
    void testSave() {
        var newEntitiesToBeSaved = new LinkedList<>(List.of(TagEntity.builder().name("tag1").build()));
        var newEntitiesSaved = new LinkedList<>(List.of(TagEntity.builder().id(1).name("tag1").build()));

        var newModelsToBeSaved = tagMapper.bulkEntity2Model(newEntitiesToBeSaved);
        var newModelsSaved = tagMapper.bulkEntity2Model(newEntitiesSaved);

        when(tagRepositoryMock.save(newEntitiesToBeSaved)).thenReturn(newEntitiesSaved);
        assertEquals(newModelsSaved, tagService.save(newModelsToBeSaved));
        verify(tagRepositoryMock, times(1)).save(newEntitiesToBeSaved);
    }

    @Test
    void testAttachTagsToPost() {
        // just stub
        doNothing().when(tagRepositoryMock).unstickFromPost(0);
        doNothing().when(tagRepositoryMock).stickToPost(List.of(0), 0);
        tagService.attachTagsToPost(List.of(0), 0);
        verify(tagRepositoryMock, times(1)).unstickFromPost(0);
        verify(tagRepositoryMock, times(1)).stickToPost(List.of(0), 0);
    }
}
