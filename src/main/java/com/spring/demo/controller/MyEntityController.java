package com.spring.demo.controller;

import com.spring.demo.entity.MyEntity;
import com.spring.demo.service.MyEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
    public ResponseEntity<CustomResponse> createEntity(@RequestBody MyEntity entity) {
        try {
            MyEntity createdEntity = entityService.create(entity);
            CustomResponse response = new CustomResponse("User added successfully", HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            CustomResponse response = new CustomResponse("Cannot create user", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @Transactional
    @GetMapping("/getAll")
    public ResponseEntity<CustomResponse> getAllEntities() {
       try{
           List<MyEntity> entities = entityService.getAll();
           if (!entities.isEmpty()) {
               CustomResponse response = new CustomResponse("Users retrieved successfully", HttpStatus.OK.value(),entities);
               return ResponseEntity.ok(response);
           } else {
               CustomResponse response = new CustomResponse("No users found. Start by creating one!", HttpStatus.OK.value(),entities);
               return ResponseEntity.ok(response);
           }

       }
       catch (Exception e) {
           CustomResponse response = new CustomResponse("Error retrieving users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       }
    }

    @Transactional
    @GetMapping("/getById/{id}")
    public ResponseEntity<CustomResponse> getEntity(@PathVariable UUID id) {
        try{
            MyEntity entity = entityService.getById(id);

            if (entity != null) {
                CustomResponse response = new CustomResponse("User retrieved successfully", HttpStatus.OK.value(), entity);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                CustomResponse response = new CustomResponse("User not found with ID: " + id, HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            CustomResponse response = new CustomResponse("Error retrieving user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Transactional
    @PutMapping("/updateById/{id}")
    public ResponseEntity<CustomResponse> updateEntity(@PathVariable UUID id, @RequestBody MyEntity updatedEntity) {
        try {
            boolean updated = entityService.update(id, updatedEntity);

            if (updated) {
                CustomResponse response = new CustomResponse("User updated successfully", HttpStatus.OK.value());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                CustomResponse response = new CustomResponse("Cannot update user with ID: " + id, HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (MethodArgumentTypeMismatchException e) {
            CustomResponse response = new CustomResponse("Error update user: " + e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @Transactional
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<CustomResponse> deleteEntity(@PathVariable UUID id) {
       try{
           boolean deleted = entityService.delete(id);

           if (deleted) {
               CustomResponse response = new CustomResponse("User deleted successfully", HttpStatus.OK.value());
               return ResponseEntity.status(HttpStatus.OK).body(response);
           } else {
               CustomResponse response = new CustomResponse("Cannot delete user with ID: " + id, HttpStatus.BAD_REQUEST.value());
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
           }
       }
       catch (Exception e) {
           CustomResponse response = new CustomResponse("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       }
    }
}