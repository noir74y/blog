package ru.noir74.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.exception.NotFoundException;
import ru.noir74.blog.model.domain.Item;
import ru.noir74.blog.model.mapper.ItemMapper;
import ru.noir74.blog.repository.ItemRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional(readOnly = true)
    public Item get(Integer id) {
        return itemMapper.entity2Model(itemRepository.findById(id).orElseThrow(() -> new NotFoundException("post is not found", "post_id = " + id)));
    }

    @Override
    @Transactional
    public Item create(Item item) {
        item.setCreated(LocalDateTime.now());
        return itemMapper.entity2Model(itemRepository.save(itemMapper.model2entity(item)));
    }

    @Override
    @Transactional
    public void update(Item item) {
        throwIfNotFound(item.getId());
        itemRepository.save(itemMapper.model2entity(item));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        throwIfNotFound(id);
        itemRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addLike(Integer id) {
        throwIfNotFound(id);
        itemRepository.addLike(id);
    }

    @Override
    @Transactional
    public void removeLike(Integer id) {
        throwIfNotFound(id);
        itemRepository.removeLike(id);
    }

    private void throwIfNotFound(Integer id) {
        if (!itemRepository.existsById(id)) throw new NotFoundException("post is not found", "post_id = " + id);
    }

}
