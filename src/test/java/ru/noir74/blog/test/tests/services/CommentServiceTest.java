package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.noir74.blog.configurations.root.MapperConfig;
import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.repositories.CommentRepository;
import ru.noir74.blog.services.CommentServiceImpl;

public class CommentServiceTest extends GenericServiceTest {
    @Autowired
    private CommentMapper commentMapper;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
//        when(commentRepository.findById(1)).thenReturn(Optional.empty());
    }

    @Test
    void testFindAllByItemId() {
    }

    @Test
    void testFindById() {

    }

    @Test
    void testCreate() {

    }

    @Test
    void testUpdate() {

    }

    @Test
    void testDelete(){

    }
}
