package ru.noir74.blog.services;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagMapper;
import ru.noir74.blog.repositories.TagRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private List<Tag> allTagsList;

    @PostConstruct
    private void populateAllTagList() {
        this.allTagsList = tagMapper.BulkEntity2Model(tagRepository.findAll());
    }

    @Override
    public List<Tag> findAll() {
        return this.allTagsList;
    }

    @Override
    public Tag findById(Integer id) {
        return this.allTagsList.stream().filter(tagEntity -> tagEntity.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public Tag findByName(String name) {
        return this.allTagsList.stream().filter(tagEntity -> tagEntity.getName().equals(name)).findAny().orElse(null);
    }

    @Override
    public List<Tag> findAllByItemId(Integer itemId) {
        return tagMapper.BulkEntity2Model(tagRepository.findAllByItemId(itemId));
    }

    @Override
    @Transactional
    public Tag save(Tag tag) {
        var newTag = tagMapper.entity2Model(this.tagRepository.save(tagMapper.model2entity(tag)));
        this.populateAllTagList();
        return newTag;
    }

    @Override
    @Transactional
    public List<Tag> save(List<Tag> tags) {
        var newTags = tagMapper.BulkEntity2Model(this.tagRepository.save(tagMapper.BulkModel2Entity(tags)));
        this.populateAllTagList();
        return newTags;
    }

    @Override
    @Transactional
    public void attachTagsToItem(List<Integer> tagIdList, Integer itemId) {
        tagRepository.unstickFromItem(itemId);
        tagRepository.stickToItem(tagIdList, itemId);
        this.populateAllTagList();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        this.tagRepository.deleteById(id);
        this.populateAllTagList();
    }

    @Override
    public boolean existsById(Integer id) {
        return this.tagRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.tagRepository.existsByName(name);
    }

}
