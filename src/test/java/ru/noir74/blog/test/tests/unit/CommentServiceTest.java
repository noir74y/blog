package ru.noir74.blog.test.tests.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.models.comment.Comment;
import ru.noir74.blog.models.comment.CommentEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CommentServiceTest extends GenericServiceTest {
//    private Comment comment;
//    private CommentEntity commentEntity;
//    private List<Comment> comments;
//    private List<CommentEntity> commentEntities;
//
//    @BeforeEach
//    void setUp() {
//        comment = Comment.builder().id(0).message("comment0").itemId(0).changed(Timestamp.from(Instant.now())).build();
//        comments = new LinkedList<>(List.of(comment));
//        commentEntity = commentMapper.model2entity(comment);
//        commentEntities = new LinkedList<>(List.of(commentEntity));
//    }
//
//    @Test
//    void testFindAllByItemId() {
//        when(commentRepositoryMock.findAllByItemId(0)).thenReturn(commentEntities);
//        assertEquals(comments, commentService.findAllByItemId(0));
//        verify(commentRepositoryMock, times(1)).findAllByItemId(0);
//    }
//
//    @Test
//    void testFindById() {
//        when(commentRepositoryMock.findById(0)).thenReturn(Optional.of(commentEntity));
//        assertEquals(commentService.findById(0), comment);
//        verify(commentRepositoryMock, times(1)).findById(0);
//    }
//
//    @Test
//    void testFindById_NotFound() {
//        when(commentRepositoryMock.findById(1)).thenThrow(new NotFoundException("comment is not found", String.valueOf(1)));
//        assertThrows(NotFoundException.class, () -> commentService.findById(1));
//        verify(commentRepositoryMock, times(1)).findById(1);
//    }
//
//    @Test
//    void testCreate() {
//        when(commentRepositoryMock.save(commentEntity)).thenReturn(0);
//        commentService.create(comment);
//        verify(commentRepositoryMock, times(1)).save(commentEntity);
//    }
//
//    @Test
//    void testUpdate() {
//        when(commentRepositoryMock.existsById(0)).thenReturn(true);
//        when(commentRepositoryMock.save(commentEntity)).thenReturn(0);
//        commentService.update(comment);
//        verify(commentRepositoryMock, times(1)).existsById(0);
//        verify(commentRepositoryMock, times(1)).save(commentEntity);
//    }
//
//    @Test
//    void testUpdate_NotFound() {
//        when(commentRepositoryMock.existsById(0)).thenReturn(false);
//        assertThrows(NotFoundException.class, () -> commentService.update(comment));
//        verify(commentRepositoryMock, times(1)).existsById(0);
//    }
//
//
//    @Test
//    void testDelete() {
//        when(commentRepositoryMock.existsById(0)).thenReturn(true);
//        doNothing().when(commentRepositoryMock).deleteById(0);
//        commentService.delete(0);
//        verify(commentRepositoryMock, times(1)).existsById(0);
//        verify(commentRepositoryMock, times(1)).deleteById(0);
//    }
//
//    @Test
//    void testDelete_NotFound() {
//        when(commentRepositoryMock.existsById(0)).thenReturn(false);
//        assertThrows(NotFoundException.class, () -> commentService.delete(0));
//        verify(commentRepositoryMock, times(1)).existsById(0);
//    }
}
