package ru.noir74.blog.services;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.exceptions.NotFoundException;
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
    @Transactional(readOnly = true)
    private void populateAllTagList() {
        this.allTagsList = tagMapper.BulkEntity2Model(tagRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getAll() {
        return this.allTagsList;
    }

    @Override
    @Transactional(readOnly = true)
    public Tag findById(Integer id) {
        return tagMapper.entity2Model(tagRepository.findById(id).orElseThrow(() -> new NotFoundException("tag is not found", "id = " + id)));

    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAllByItemId(Integer itemId) {
        return tagMapper.BulkEntity2Model(tagRepository.findAllByItemId(itemId));
    }

    @Override
    @Transactional
    public void save(Tag tag) {
        this.tagRepository.save(tagMapper.model2entity(tag));
        this.populateAllTagList();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        this.tagRepository.deleteById(id);
        this.populateAllTagList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return this.tagRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return this.tagRepository.existsByName(name);
    }

}
