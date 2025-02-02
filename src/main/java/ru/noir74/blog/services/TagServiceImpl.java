package ru.noir74.blog.services;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.noir74.blog.models.tag.Tag;
import ru.noir74.blog.models.tag.TagMapper;
import ru.noir74.blog.repositories.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private List<Tag> allTagsList;

    @PostConstruct
    private void initializeAllTagsList() {
        this.allTagsList = tagMapper.BulkEntity2Model(tagRepository.findAll());
    }

    @Override
    public List<Tag> getAll() {
        return this.allTagsList;
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Tag> findAllByItemId(Integer itemId) {
        return List.of();
    }

    @Override
    public Tag save(Tag tag) {
        this.initializeAllTagsList();
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        this.initializeAllTagsList();
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }
}
