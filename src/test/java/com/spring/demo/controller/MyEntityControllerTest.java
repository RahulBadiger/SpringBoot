package com.spring.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.demo.entity.MyEntity;
import com.spring.demo.repository.MyEntityRepository;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
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
        MockitoAnnotations.openMocks(this);
    }

    @MockBean
    private MyEntityRepository entityRepository;

    @Test
    public void create_Entity_Success_Scenario() throws Exception {
        MyEntity myEntity = new MyEntity();
        myEntity.setCreatedBy("Beast");
        myEntity.setUpdatedBy("Beast");
        Map<String, Object> dataSchema = new HashMap<>();
        dataSchema.put("name", "Beast");
        myEntity.setDataSchema(dataSchema);
        Map<String, Object> routerConfig = new HashMap<>();
        routerConfig.put("name", "Beast");
        myEntity.setRouterConfig(routerConfig);
        myEntity.setStatus(MyEntity.StatusEnum.Draft);
        when(entityService.create(any(MyEntity.class))).thenReturn(myEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/dataset/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(myEntity)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Entity created successfully."))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));
    }


    @Test
    public void create_Entity_Failure_Scenario() throws Exception {

        MyEntity myEntity = new MyEntity();

        when(entityService.create(any(MyEntity.class))).thenReturn(isNull());

        mockMvc.perform(MockMvcRequestBuilders.post("/dataset/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(myEntity)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void getAll_Entities_Success_Scenario() throws Exception {

        MyEntity myEntity = new MyEntity();
        myEntity.setCreatedBy("Beast");
        myEntity.setUpdatedBy("Beast");
        Map<String, Object> dataSchema = new HashMap<>();
        dataSchema.put("name", "Beast");
        myEntity.setDataSchema(dataSchema);
        Map<String, Object> routerConfig = new HashMap<>();
        routerConfig.put("name", "Beast");
        myEntity.setRouterConfig(routerConfig);
        myEntity.setStatus(MyEntity.StatusEnum.Draft);
        List<MyEntity> entityList = new ArrayList<>();
        entityList.add(myEntity);
        when(entityService.getAll()).thenReturn(entityList);
        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Fetched all entities."))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));
    }

    @Test
    public void getAll_Entities_Failure_Scenario() throws Exception {

        List<MyEntity> entityList = new ArrayList<>();
        when(entityService.getAll()).thenReturn(entityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getAll"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The database is empty. Start by creating an entity."))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void get_Entities_ById_Success_Scenario() throws Exception {
        UUID uuid = UUID.randomUUID();
        MyEntity myEntity = new MyEntity();
        myEntity.setCreatedBy("Beast");
        myEntity.setUpdatedBy("Beast");
        Map<String, Object> dataSchema = new HashMap<>();
        dataSchema.put("name", "Beast");
        myEntity.setDataSchema(dataSchema);
        Map<String, Object> routerConfig = new HashMap<>();
        routerConfig.put("name", "Beast");
        myEntity.setRouterConfig(routerConfig);
        myEntity.setStatus(MyEntity.StatusEnum.Draft);
        when(entityService.create(myEntity)).thenReturn(myEntity);

        when(entityService.getById(any(UUID.class))).thenReturn(myEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getById/" + uuid))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Entity fetched successfully with ID: " + uuid))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));
    }

    @Test
    public void get_Entities_ById_Failure_Scenario() throws Exception {
        UUID uuid = UUID.randomUUID();
        MyEntity myEntity = new MyEntity();

        when(entityService.getById(uuid)).thenReturn(isNull());
        mockMvc.perform(MockMvcRequestBuilders.get("/dataset/getById/" + uuid))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cannot find entity with ID: " + uuid))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void update_ById_Success_Scenario() throws Exception {

        UUID uuid = UUID.randomUUID();
        MyEntity myEntity = new MyEntity();
        myEntity.setCreatedBy("Beast");
        myEntity.setUpdatedBy("Beast");
        Map<String, Object> dataSchema = new HashMap<>();
        dataSchema.put("name", "Beast");
        myEntity.setDataSchema(dataSchema);
        Map<String, Object> routerConfig = new HashMap<>();
        routerConfig.put("name", "Beast");
        myEntity.setRouterConfig(routerConfig);
        myEntity.setStatus(MyEntity.StatusEnum.Draft);
        when(entityService.create(myEntity)).thenReturn(myEntity);

        when(entityRepository.existsById(any(UUID.class))).thenReturn(true);

        when(entityService.update(any(MyEntity.class), any(UUID.class))).thenReturn(myEntity);
        mockMvc.perform(MockMvcRequestBuilders.put("/dataset/updateById/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(myEntity)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Entity updated successfully with ID: " + uuid))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));

    }

    @Test
    public void updates_ById_Failure_Scenario() throws Exception {

        UUID uuid = UUID.randomUUID();
        MyEntity myEntity = new MyEntity();

        when(entityRepository.existsById(any(UUID.class))).thenReturn(false);

        when(entityService.update(any(MyEntity.class), any(UUID.class))).thenReturn(myEntity);
        mockMvc.perform(MockMvcRequestBuilders.put("/dataset/updateById/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(myEntity)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to update entity with ID: " + uuid))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    public void delete_ById_Success_Scenario() throws Exception {
        UUID uuid = UUID.randomUUID();
        MyEntity myEntity = new MyEntity();
        myEntity.setCreatedBy("Beast");
        myEntity.setUpdatedBy("Beast");
        Map<String, Object> dataSchema = new HashMap<>();
        dataSchema.put("name", "Beast");
        myEntity.setDataSchema(dataSchema);
        Map<String, Object> routerConfig = new HashMap<>();
        routerConfig.put("name", "Beast");
        myEntity.setRouterConfig(routerConfig);
        myEntity.setStatus(MyEntity.StatusEnum.Draft);
        when(entityService.create(myEntity)).thenReturn(myEntity);

        when(entityRepository.existsById(any(UUID.class))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/dataset/deleteById/" + uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Entity deleted successfully with ID: " + uuid))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));
    }

    @Test
    public void delete_ById_Failure_Scenario() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(!entityRepository.existsById(any(UUID.class))).thenReturn(false);
        doNothing().when(entityService).delete(uuid);
        mockMvc.perform(MockMvcRequestBuilders.delete("/dataset/deleteById/" + uuid))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to delete entity with ID: " + uuid))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }

}
