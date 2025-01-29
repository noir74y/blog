package ru.noir74.blog.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.exceptions.NotFoundException;
import ru.noir74.blog.models.item.Item;
import ru.noir74.blog.models.item.ItemBrief;
import ru.noir74.blog.models.item.ItemMapper;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagEntity;
import ru.noir74.blog.models.tag.TagMapper;
import ru.noir74.blog.repositories.ItemRepository;
import ru.noir74.blog.repositories.TagRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public List<Tag> getAll() {
        return tagMapper.BulkEntity2Model(new ArrayList<>(tagRepository.getAll()));
    }
}
