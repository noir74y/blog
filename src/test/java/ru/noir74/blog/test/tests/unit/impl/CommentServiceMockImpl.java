package ru.noir74.blog.test.tests.unit.impl;

import ru.noir74.blog.mappers.CommentMapper;
import ru.noir74.blog.repositories.intf.CommentRepository;
import ru.noir74.blog.services.impl.CommentServiceImpl;
import ru.noir74.blog.test.tests.unit.intf.CommentServiceMock;

public class CommentServiceMockImpl extends CommentServiceImpl implements CommentServiceMock {

    public CommentServiceMockImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        super(commentRepository, commentMapper);
    }
}
