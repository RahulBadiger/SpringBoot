package com.spring.demo.controller;

import com.spring.demo.entity.MyEntity;
import com.spring.demo.exception.MyEntityException;
import com.spring.demo.repository.MyEntityRepository;
import com.spring.demo.service.MyEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/dataset")
public class MyEntityController {
    private final MyEntityService entityService;

    @Autowired
    public MyEntityController(MyEntityService entityService) {
        this.entityService = entityService;
    }

    @Autowired
    MyEntityRepository entityRepository;

    @PostMapping("/create")
    public ResponseEntity<CustomResponse> createEntity(@RequestBody MyEntity entity) {
        try {
            MyEntity myEntity1 = entityService.create(entity);
            if (myEntity1 == null) {
                throw new MyEntityException("Failed to create user please try again..! ");
            }
            CustomResponse response = new CustomResponse("Entity created successfully.", HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            CustomResponse response = new CustomResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<CustomResponse> getAllEntities() {
        try {
            List<MyEntity> entityList = entityService.getAll();
            if (entityList.isEmpty()) {
                throw new MyEntityException("The database is empty. Start by creating an entity.");
            }
            CustomResponse response = new CustomResponse("Fetched all entities.", HttpStatus.OK.value(), entityList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            CustomResponse response = new CustomResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CustomResponse> getEntity(@PathVariable UUID id) {
        try {
            Optional<MyEntity> optionalMyEntity = Optional.ofNullable(entityService.getById(id));
            if (optionalMyEntity.isEmpty()) {
                throw new MyEntityException("Cannot find entity with ID: " + id);
            }
            CustomResponse response = new CustomResponse("Entity fetched successfully with ID: " + id, HttpStatus.OK.value(), optionalMyEntity);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            CustomResponse response = new CustomResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<CustomResponse> updateEntity(@PathVariable UUID id, @RequestBody MyEntity myEntity) {
        try {
            if (!entityRepository.existsById(id)) {
                throw new MyEntityException("Failed to update entity with ID: " + id);
            }
            MyEntity updatedEntity = entityService.update(myEntity, id);
            CustomResponse response = new CustomResponse("Entity updated successfully with ID: " + id,
                    HttpStatus.OK.value(), updatedEntity);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            CustomResponse response = new CustomResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<CustomResponse> deleteEntity(@PathVariable UUID id) {
        try {
            if (!entityRepository.existsById(id)) {
                throw new MyEntityException("Failed to delete entity with ID: " + id);
            }
            entityService.delete(id);
            CustomResponse response = new CustomResponse("Entity deleted successfully with ID: " + id,
                    HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            CustomResponse response = new CustomResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}