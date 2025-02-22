package ru.noir74.blog.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.mappers.TagMapper;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.repositories.TagRepository;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Setter
    private List<Tag> allTags;

    @PostConstruct
    public void populateAllTags() {
        allTags = new LinkedList<>(tagMapper.bulkEntity2Model(tagRepository.findAll()));
        tagMapper.setName2TagMap(allTags);
    }

    @Override
    public List<Tag> findAll() {
        return allTags;
    }

    @Override
    public Tag findById(Integer id) {
        return allTags.stream().filter(tagEntity -> tagEntity.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public List<Tag> findAllByItemId(Integer itemId) {
        return tagMapper.bulkEntity2Model(tagRepository.findAllByItemId(itemId));
    }

    @Override
    @Transactional
    public List<Tag> save(List<Tag> tags) {
        var newTags = tagMapper.bulkEntity2Model(tagRepository.save(tagMapper.bulkModel2Entity(tags)));
        populateAllTags();
        return newTags;
    }

    @Override
    @Transactional
    public void attachTagsToItem(List<Integer> tagIdList, Integer itemId) {
        tagRepository.unstickFromItem(itemId);
        tagRepository.stickToItem(tagIdList, itemId);
        populateAllTags();
    }
}
