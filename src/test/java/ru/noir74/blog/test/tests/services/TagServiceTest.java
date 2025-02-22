package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.noir74.blog.models.tag.TagEntity;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;

public class TagServiceTest extends GenericServiceTest {
    private LinkedList<TagEntity> tags;

    @BeforeEach
    void setUp() {
        this.tags = new LinkedList<>(List.of(
                TagEntity.builder().id(1).name("tag1").build(),
                TagEntity.builder().id(2).name("tag2").build(),
                TagEntity.builder().id(3).name("tag3").build()
        ));
        doAnswer(invocationOnMock ->
        {
            tagService.setAllTagsList(tagMapper.BulkEntity2Model(tags));
            return null;
        })
                .when(tagService).populateAllTagList();
        tagService.populateAllTagList();
    }

    @Test
    void testFindAll() {
        assertEquals(tagMapper.BulkEntity2Model(tags), tagService.findAll());
    }

    @Test
    void testFindById() {
    }

    @Test
    void testFindAllByItemId() {
    }

    @Test
    void testSave() {
    }

    @Test
    void testAttachTagsToItem() {
    }
}
