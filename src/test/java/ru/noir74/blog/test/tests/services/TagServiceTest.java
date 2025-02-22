package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.models.tag.TagEntity;
import ru.noir74.blog.repositories.TagRepository;
import ru.noir74.blog.services.TagServiceImpl;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;

public class TagServiceTest extends GenericServiceTest {
    private LinkedList<TagEntity> tags;

    @Autowired
    private TagMapper tagMapper;

    @Mock
    private TagRepository tagRepository;

    @Spy
    private TagServiceImpl tagService;

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
