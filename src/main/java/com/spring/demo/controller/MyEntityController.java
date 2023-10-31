package com.spring.demo.controller;

import com.spring.demo.entity.MyEntity;
import com.spring.demo.service.MyEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dataset")
public class MyEntityController {
    private final MyEntityService entityService;

    @Autowired
    public MyEntityController(MyEntityService entityService) {
        this.entityService = entityService;
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<MyEntity> createEntity(@RequestBody MyEntity entity) {
        MyEntity createdEntity = entityService.create(entity);
        return new ResponseEntity<>(createdEntity, HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping("/getById/{id}")
    public ResponseEntity<MyEntity> getEntity(@PathVariable UUID id) {
        MyEntity entity = entityService.getById(id);
        if (entity != null) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @GetMapping("/getAll")
    public ResponseEntity<List<MyEntity>> getAllEntities() {
        List<MyEntity> entities = entityService.getAll();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/updateById/{id}")
    public ResponseEntity<MyEntity> updateEntity(@PathVariable UUID id, @RequestBody MyEntity updatedEntity) {
        MyEntity entity = entityService.update(id, updatedEntity);
        if (entity != null) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable UUID id) {
        boolean deleted = entityService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
