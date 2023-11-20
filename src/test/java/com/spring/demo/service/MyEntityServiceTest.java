package com.spring.demo.service;

import com.spring.demo.entity.MyEntity;
import com.spring.demo.repository.MyEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


class MyEntityServiceTest {

    @Mock
    private MyEntityRepository entityRepository;
    @Mock
    private MyEntityService entityService;
    AutoCloseable autoCloseable;
    @Mock
    MyEntity entity;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        entityService = new MyEntityService(entityRepository);

        UUID uuid = UUID.randomUUID();
        Map<String, Object> dataSchema = new HashMap<>();
        dataSchema.put("name", "rahul");
        Map<String, Object> routerconfig = new HashMap<>();
        routerconfig.put("Speed", "1gb");
        String createdBy = "rahul";
        String updatedBy = "rahul";

        entity = new MyEntity();
        entity.setDataSchema(dataSchema);
        entity.setRouterConfig(routerconfig);
        entity.setUpdatedBy(updatedBy);
        entity.setCreatedBy(createdBy);
        entity.setStatus(MyEntity.StatusEnum.Live);


        entityRepository.save(entity);


    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testCreate() {


        when(entityRepository.save(entity)).thenReturn(entity);
        assertThat(entityService.create(entity)).isEqualTo(entity);


    }

    @Test
    void testGetAll() {

        when(entityRepository.findAll()).thenReturn(
                new ArrayList<MyEntity>(Collections.singleton(entity))
        );
        assertThat(entityService.getAll().get(0).getCreatedBy()).isEqualTo(entity.getCreatedBy());
    }


    @Test
    void testGetById() {

        UUID uuid = UUID.randomUUID();

        when(entityRepository.findById(uuid)).thenReturn(Optional.ofNullable(entity));
        assertThat(entityService.getById(uuid).getCreatedBy()).isEqualTo(entity.getCreatedBy());

    }


    @Test
    void testUpdateEntity() throws Exception {

        UUID uuid = UUID.randomUUID();
        MyEntity updatedEntity = new MyEntity();
        updatedEntity.setUpdatedBy("rahul");

        when(entityRepository.existsById(uuid)).thenReturn(true);
        when(entityRepository.save(updatedEntity)).thenReturn(updatedEntity);

        assertThat(entityService.update(updatedEntity, uuid).getUpdatedBy()).isEqualTo(entity.getUpdatedBy());

    }

    @Test
    void testNotUpdateEntity() throws Exception {

        UUID uuid = UUID.randomUUID();
        MyEntity updatedEntity = new MyEntity();

        when(!entityRepository.existsById(uuid)).thenReturn(false);
        when(entityRepository.save(updatedEntity)).thenReturn(updatedEntity);

        assertThat(entityService.update(updatedEntity, uuid));
    }


    @Test
    void testDelete() {
        UUID id = UUID.randomUUID();
        when(entityRepository.existsById(id)).thenReturn(true);
        doNothing().when(entityRepository).deleteById(id);
    }
    @Test
    void testNotDelete() {
        UUID id = UUID.randomUUID();
        when(!entityRepository.existsById(id)).thenReturn(false);
        doNothing().when(entityRepository).deleteById(id);
    }


}