package ru.noir74.blog.test.tests.unit.impl;

import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.repositories.intf.TagRepository;
import ru.noir74.blog.services.impl.TagServiceImpl;
import ru.noir74.blog.test.tests.unit.intf.TagServiceMock;

public class TagServiceMockImpl extends TagServiceImpl implements TagServiceMock {
    public TagServiceMockImpl(TagRepository tagRepository, TagMapper tagMapper) {
        super(tagRepository, tagMapper);
    }
}
