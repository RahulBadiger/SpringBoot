package com.spring.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.demo.entity.MyEntity;
import com.spring.demo.service.MyEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyEntityController.class)
public class MyEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyEntityService entityService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private MyEntityController myEntityController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_entity_success_scenario() throws Exception {
        MyEntity entity = new MyEntity();
        UUID uuid=UUID.randomUUID();
        when(entityService.create(any(MyEntity.class))).thenReturn(entity);

        mockMvc.perform(MockMvcRequestBuilders.post("/dataset/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entity)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User added successfully"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value()));

    }

    @Test
    public void testNotCreateEntity() throws Exception {
        MyEntity entity = new MyEntity();
        when(entityService.create(any(MyEntity.class))).thenThrow(HttpMessageNotReadableException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/dataset/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cannot create user"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    public void testGetAllEntities() throws Exception {
        MyEntity entity = new MyEntity();

        List<MyEntity> entityList = new ArrayList<>();
        entityList.add(entity);
        when(entityService.getAll()).thenReturn(entityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Users retrieved successfully"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testNotGetAllEntities() throws Exception {
        MyEntity entity = new MyEntity();
        when(entityService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("No users found. Start by creating one!"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    public void testNotGetAllEntities_Exce() throws Exception {
        MyEntity entity = new MyEntity();
        when(entityService.getAll()).thenThrow(MethodArgumentTypeMismatchException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getAll"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error retrieving users: null"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @Test
    public void testGetEntityById() throws Exception {
        UUID entityId = UUID.randomUUID();
        MyEntity entity = new MyEntity();
        entity.setId(entityId);

        when(entityService.getById(entityId)).thenReturn(entity);

        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getById/" + entityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User retrieved successfully"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
    }

    @Test
    public void testNotGetEntityById() throws Exception {
        UUID entityId = UUID.randomUUID();
        MyEntity entity = new MyEntity();

        when(entityService.getById(entityId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getById/" + entityId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with ID: "+ entityId))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.NOT_FOUND.value()));
    }
    @Test
    public void testNotGetEntityById_Exce() throws Exception {
        UUID entityId = UUID.randomUUID();
        MyEntity entity = new MyEntity();

        when(entityService.getById(entityId)).thenThrow(MethodArgumentTypeMismatchException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getById/" + entityId))
                .andExpect(jsonPath("$.message").value("Error retrieving user: null"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void testNotUpdateEntity() throws Exception {
        MyEntity updatedEntity = new MyEntity();

        UUID entityId = UUID.randomUUID();
        updatedEntity.setId(entityId);

        when(entityService.update(entityId,updatedEntity)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/dataset/updateById/"+ entityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cannot update user with ID: "+ entityId))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testNotUpdateEntity_Exe() throws Exception {


        UUID entityId = UUID.randomUUID();
        MyEntity updatedEntity = new MyEntity();
        doThrow(MethodArgumentTypeMismatchException.class).when(entityService).update(any(UUID.class),
                any(MyEntity.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/dataset/updateById/"+ entityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error update user: null"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
    }


    @Test
    public void testUpdateEntity() throws Exception {
        UUID entityId = UUID.randomUUID();
        MyEntity updatedEntity = new MyEntity();
        when(entityService.update(any(UUID.class),any(MyEntity.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/dataset/updateById/" + entityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEntity)))
                .andDo(print())
                .andExpect(jsonPath("$.message").value("User updated successfully"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(status().isOk());
    }


    @Test
    public void testDeleteEntity() throws Exception {

        UUID uuid=UUID.randomUUID();

        when(entityService.delete(uuid)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/dataset/deleteById/" + uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User deleted successfully"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
    }

    @Test
    public void testNotDeleteEntity() throws Exception {

        UUID uuid=UUID.randomUUID();

        when(entityService.delete(uuid)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/dataset/deleteById/" + uuid))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cannot delete user with ID: "+ uuid))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testNotDeleteEntity_Exec() throws Exception {

        UUID uuid=UUID.randomUUID();

        when(entityService.delete(uuid)).thenThrow(MethodArgumentTypeMismatchException.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/dataset/deleteById/" + uuid))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error deleting user: null"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
