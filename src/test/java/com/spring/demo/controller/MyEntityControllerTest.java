package com.spring.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.demo.controller.MyEntityController;
import com.spring.demo.entity.MyEntity;
import com.spring.demo.service.MyEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MyEntityController.class)
public class MyEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyEntityService entityService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateEntity() throws Exception {
        MyEntity entityToCreate = new MyEntity();
        entityToCreate.setCreatedBy("Test Entity");

        when(entityService.create(any(MyEntity.class))).thenReturn(entityToCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/dataset/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entityToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User added successfully"))
                .andExpect(jsonPath("$.statusCode").value(201));
    }

    @Test
    public void testGetAllEntities() throws Exception {
        MyEntity entity = new MyEntity();
        entity.setId(UUID.randomUUID());
        entity.setUpdatedBy("Test Entity");

        List<MyEntity> entityList = Collections.singletonList(entity);

        when(entityService.getAll()).thenReturn(entityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Users retrieved successfully"))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].updatedBy").value("Test Entity"));
    }

    @Test
    public void testGetEntityById() throws Exception {
        UUID entityId = UUID.randomUUID();
        MyEntity entity = new MyEntity();
        entity.setId(entityId);
        entity.setCreatedBy("Test Entity");

        when(entityService.getById(entityId)).thenReturn(entity);

        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getById/" + entityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User retrieved successfully"))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.createdBy").value("Test Entity"));
    }

    @Test
    public void testUpdateEntity() throws Exception {
        UUID entityId = UUID.randomUUID();
        MyEntity updatedEntity = new MyEntity();
        updatedEntity.setId(entityId);
        updatedEntity.setCreatedBy("Updated Entity");

        when(entityService.update(entityId, updatedEntity)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/dataset/updateById/" + entityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User updated successfully"))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @Test
    public void testDeleteEntity() throws Exception {
        UUID entityId = UUID.randomUUID();

        when(entityService.delete(entityId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/dataset/deleteById/" + entityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User deleted successfully"))
                .andExpect(jsonPath("$.statusCode").value(200));
    }
}
