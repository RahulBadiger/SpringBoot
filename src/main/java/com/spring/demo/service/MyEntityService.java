package com.spring.demo.service;

import com.spring.demo.entity.MyEntity;
import com.spring.demo.repository.MyEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MyEntityService {
    private final MyEntityRepository entityRepository;

    @Autowired
    public MyEntityService(MyEntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public MyEntity create(MyEntity entity) {
        if (entity.getId() != null) {
            throw new IllegalArgumentException("New entities cannot have an ID.");
        }
        return entityRepository.save(entity);
    }

    public List<MyEntity> getAll() {
        return entityRepository.findAll();
    }

    public MyEntity getById(UUID id) {
        Optional<MyEntity> entityOptional = entityRepository.findById(id);
        return entityOptional.orElse(null);
    }

    public MyEntity update(UUID id, MyEntity updatedEntity) {
        if (!entityRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found with ID: " + id);
        }
        updatedEntity.setId(id);
        return entityRepository.save(updatedEntity);
    }

    public boolean delete(UUID id) {
        if (!entityRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found with ID: " + id);
        }
        entityRepository.deleteById(id);
        return true;
    }
}
