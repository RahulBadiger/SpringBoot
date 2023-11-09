package com.spring.demo.service;

import com.spring.demo.controller.*;
import com.spring.demo.entity.MyEntity;
import com.spring.demo.repository.MyEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        MyEntity createdEntity = entityRepository.save(entity);
        if (createdEntity == null) {
            throw new IllegalArgumentException("Failed to create the user. Please check your input.");
        }
        return createdEntity;
    }

    public List<MyEntity> getAll() {
        try {
            List<MyEntity> entities = entityRepository.findAll();
            if (entities.isEmpty()) {
                throw new IllegalArgumentException("No users found. Start by creating one!");
            }
            return entities;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching users.");
        }
    }

    public MyEntity getById(UUID id) {
        try {
            Optional<MyEntity> entityOptional = entityRepository.findById(id);
            return entityOptional.orElse(null);
        } catch (Exception e) {
            throw new IllegalArgumentException("An error occurred while fetching user with ID: " + id);
        }
    }

    public boolean update(UUID id, MyEntity updatedEntity) {
        if (entityRepository.existsById(id)) {
            updatedEntity.setId(id);
            entityRepository.save(updatedEntity);
            return true;
        } else {
            return false;
        }
    }





    public boolean delete(UUID id) {
        if (entityRepository.existsById(id)) {
            entityRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }}