package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.noir74.blog.models.tag.TagEntity;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class TagServiceTest extends GenericServiceTest {
    TagEntity entity0;
    TagEntity entity1;
    private LinkedList<TagEntity> tagEntities;

    @BeforeEach
    void setUp() {
        entity0 = TagEntity.builder().id(0).name("tag0").build();
        entity1 = TagEntity.builder().id(1).name("tag1").build();

        tagEntities = new LinkedList<>(List.of(entity0, entity1));

        doAnswer(invocationOnMock ->
        {
            tagService.setAllTagsList(tagMapper.BulkEntity2Model(tagEntities));
            return null;
        })
                .when(tagService).populateAllTagList();
        tagService.populateAllTagList();

        //when(tagRepository.findAllByItemId(0)).thenReturn(List.of(entity0));
    }

    @Test
    void testFindAll() {
        assertEquals(tagMapper.BulkEntity2Model(tagEntities), tagService.findAll());
    }

    @Test
    void testFindById() {
        assertEquals(tagMapper.entity2Model(tagEntities.getFirst()), tagService.findById(0));
    }

    @Test
    void testFindAllByItemId() {
        //assertEquals(tagMapper.entity2Model(entity0), tagService.findAllByItemId(0));
    }

    @Test
    void testSave() {
    }

    @Test
    void testAttachTagsToItem() {
    }
}
