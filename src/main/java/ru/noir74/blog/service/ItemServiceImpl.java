package ru.noir74.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noir74.blog.model.domain.Item;
import ru.noir74.blog.model.mapper.ItemMapper;
import ru.noir74.blog.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional(readOnly = true)
    public Item get(Integer id) {
        return itemMapper.entity2Model(itemRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Item create(Item item) {
        return itemMapper.entity2Model(itemRepository.save(itemMapper.model2entity(item)));
    }

    @Override
    @Transactional
    public void update(Item item) {

    }

    @Override
    @Transactional
    public void delete(Integer id) {

    }

    @Override
    @Transactional
    public void addLike(Integer id) {

    }

    @Override
    @Transactional
    public void removeLike(Integer id) {

    }
}
