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

        MyEntity createdEntity = entityRepository.save(entity);

        return createdEntity;
    }

    public List<MyEntity> getAll() {
        List<MyEntity> entities = entityRepository.findAll();
        return entities;

    }

    public MyEntity getById(UUID id) {
        Optional<MyEntity> entityOptional = entityRepository.findById(id);
        return entityOptional.orElse(null);
    }

    public MyEntity update(MyEntity myEntity, UUID id) {
        return entityRepository.save(myEntity);
    }

    public void delete(UUID id) {
        entityRepository.deleteById(id);
    }
}