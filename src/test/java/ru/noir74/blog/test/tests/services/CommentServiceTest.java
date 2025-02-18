package ru.noir74.blog.test.tests.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.noir74.blog.repositories.CommentRepository;
import ru.noir74.blog.services.CommentService;
import ru.noir74.blog.services.CommentServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest  {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;


    @Test
    void myTest() {
        when(commentRepository.findById(1)).thenReturn(null);
        assertNull(commentService.findById(1));
    }
}
