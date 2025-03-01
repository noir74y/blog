package ru.noir74.blog.test.tests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.mappers.ItemImageMapper;
import ru.noir74.blog.mappers.ItemMapper;
import ru.noir74.blog.models.item.*;
import ru.noir74.blog.repositories.intf.ItemRepository;
import ru.noir74.blog.services.intf.ItemService;
import ru.noir74.blog.test.generics.GenericServiceTest;
import ru.noir74.blog.test.tests.service.intf.CommentServiceMock;
import ru.noir74.blog.test.tests.service.intf.TagServiceMock;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ItemServiceTest extends GenericServiceTest {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemImageMapper itemImageMapper;
    @Autowired
    private ItemRepository itemRepositoryMock;
    @Autowired
    private TagServiceMock tagServiceMock;
    @Autowired
    private CommentServiceMock commentServiceMock;
    @Autowired
    private ItemService itemService;

    private Item item;
    private ItemEntity itemEntity;
    private List<ItemBrief> itemBriefs;
    private List<ItemEntityBrief> itemEntityBriefs;
    private ItemImage itemImage;
    private ItemImageEntity itemImageEntity;

    @BeforeEach
    void setUp() {
        var file = new MockMultipartFile(
                "name",
                "originalFilename.jpeg",
                "image/jpeg",
                new byte[]{(byte) 0x00});

        item = Item.builder()
                .id(0)
                .title("title")
                .message("message")
                .likes(0)
                .comments(new ArrayList<>())
                .tags(new ArrayList<>())
                .file(file)
                .changed(Timestamp.from(Instant.now())).build();
        itemEntity = itemMapper.model2entity(item);

        var itemEntityBrief = ItemEntityBrief.builder()
                .id(item.getId())
                .title(item.getTitle())
                .message(item.getMessage())
                .likes(item.getLikes())
                .commentsCounter(item.getLikes())
                .tagsCSV("")
                .build();
        itemEntityBriefs = new LinkedList<>(List.of(itemEntityBrief));
        itemBriefs = itemMapper.bulkEntityBrief2ModelBrief(itemEntityBriefs);

        itemImage = ItemImage.builder().id(item.getId()).file(file).build();
        itemImageEntity = itemImageMapper.model2entity(itemImage);

        reset(itemRepositoryMock);
        reset(tagServiceMock);
        reset(commentServiceMock);
    }

    @Test
    void testFindPage() {
        when(itemRepositoryMock.findByPage(0, 10)).thenReturn(itemEntityBriefs);
        assertEquals(itemBriefs, itemService.findPage("0", "10", ""));
        verify(itemRepositoryMock, times(1)).findByPage(0, 10);
    }

    @Test
    void testFindById() {
        when(itemRepositoryMock.findById(0)).thenReturn(Optional.of(itemEntity));
        assertEquals(item, itemService.findById(0));
        verify(itemRepositoryMock, times(1)).findById(0);
    }

    @Test
    void testFindById_NotFound() {
        when(itemRepositoryMock.findById(1)).thenThrow(new NotFoundException("item is not found", String.valueOf(1)));
        assertThrows(NotFoundException.class, () -> itemService.findById(1));
        verify(itemRepositoryMock, times(1)).findById(1);
    }

    @Test
    void testFindImageById() throws IOException {
        when(itemRepositoryMock.findImageById(item.getId())).thenReturn(itemImageEntity);
        itemService.findImageById(itemImage);
        verify(itemRepositoryMock, times(1)).findImageById(item.getId());
    }

    @Test
    void testCreate() throws IOException {
        when(itemRepositoryMock.save(itemEntity)).thenReturn(item.getId());
        doNothing().when(itemRepositoryMock).saveImageById(itemImageEntity);
        doNothing().when(tagServiceMock).attachTagsToItem(List.of(), item.getId());

        itemService.create(item);

        verify(itemRepositoryMock, times(1)).save(itemEntity);
        verify(itemRepositoryMock, times(1)).saveImageById(itemImageEntity);
        verify(tagServiceMock, times(1)).attachTagsToItem(List.of(), item.getId());
    }

    @Test
    void testUpdate() throws IOException {
        when(itemRepositoryMock.existsById(item.getId())).thenReturn(true);
        when(itemRepositoryMock.save(itemEntity)).thenReturn(item.getId());
        itemService.update(item);
        verify(itemRepositoryMock, times(1)).existsById(item.getId());
        verify(itemRepositoryMock, times(1)).save(itemEntity);
    }

    @Test
    void testUpdate_NotFound() {
        when(itemRepositoryMock.existsById(item.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> itemService.update(item));
        verify(itemRepositoryMock, times(1)).existsById(item.getId());
    }

    @Test
    void testSetImageById() throws IOException {
        doNothing().when(itemRepositoryMock).saveImageById(itemImageEntity);
        itemService.setImageById(itemImage);
        verify(itemRepositoryMock, times(1)).saveImageById(itemImageEntity);
    }

    @Test
    void testDelete() {
        when(itemRepositoryMock.existsById(item.getId())).thenReturn(true);
        doNothing().when(itemRepositoryMock).deleteById(item.getId());
        itemService.delete(item.getId());
        verify(itemRepositoryMock, times(1)).existsById(item.getId());
        verify(itemRepositoryMock, times(1)).deleteById(item.getId());
    }

    @Test
    void testDelete_NotFound() {
        when(itemRepositoryMock.existsById(item.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> itemService.delete(item.getId()));
        verify(itemRepositoryMock, times(1)).existsById(item.getId());
    }
}
