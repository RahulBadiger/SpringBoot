package com.spring.demo.service;

import com.spring.demo.entity.MyEntity;
import com.spring.demo.repository.MyEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


class MyEntityServiceTest {

    @Mock
    private MyEntityRepository entityRepository;
    private MyEntityService entityService;
    AutoCloseable autoCloseable;
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
        mock(MyEntity.class);
        mock(MyEntityRepository.class);

        when(entityRepository.save(entity)).thenReturn(entity);
        assertThat(entityService.create(entity)).isEqualTo(entity);


    }

    @Test
    void testGetAll() {
        mock(MyEntity.class);
        mock(MyEntityRepository.class);

        when(entityRepository.findAll()).thenReturn(
                new ArrayList<MyEntity>(Collections.singleton(entity))
        );
        assertThat(entityService.getAll().get(0).getCreatedBy()).isEqualTo(entity.getCreatedBy());
    }

    @Test
    void testGetById() {

        mock(MyEntity.class);
        mock(MyEntityRepository.class);
        UUID uuid = UUID.randomUUID();

        when(entityRepository.findById(uuid)).thenReturn(Optional.ofNullable(entity));
        assertThat(entityService.getById(uuid).getCreatedBy()).isEqualTo(entity.getCreatedBy());

    }


    @Test
    public void testUpdateEntity() throws Exception {

        UUID uuid = UUID.randomUUID();
        MyEntity updatedEntity = new MyEntity();

        when(entityRepository.existsById(uuid)).thenReturn(true);
        when(entityRepository.save(updatedEntity)).thenReturn(updatedEntity);

        assertThat(entityService.update(uuid, updatedEntity)).isTrue();
    }

    @Test
    public void testNotUpdateEntity() throws Exception {

        UUID uuid = UUID.randomUUID();
        MyEntity updatedEntity = new MyEntity();

        when(entityRepository.existsById(uuid)).thenReturn(false);
        when(entityRepository.save(updatedEntity)).thenReturn(updatedEntity);

        assertThat(entityService.update(uuid, updatedEntity)).isFalse();
    }


    @Test
    void testDelete() {
        mock(MyEntity.class);
        mock(MyEntityRepository.class, Mockito.CALLS_REAL_METHODS);
        UUID uuid = UUID.randomUUID();
        entity.setId(uuid);
        entityRepository.save(entity);

        when(entityRepository.existsById(uuid)).thenReturn(true);


        doAnswer(Answers.CALLS_REAL_METHODS).when(
                entityRepository).deleteById(uuid);
        assertThat(entityService.delete(uuid)).isEqualTo(true);


    }
    @Test
    void testNotDelete() {
        mock(MyEntity.class);
        mock(MyEntityRepository.class, Mockito.CALLS_REAL_METHODS);
        UUID uuid = UUID.randomUUID();


        when(entityRepository.existsById(uuid)).thenReturn(false);


        doAnswer(Answers.CALLS_REAL_METHODS).when(
                entityRepository).deleteById(uuid);
        assertThat(entityService.delete(uuid)).isEqualTo(false);


    }
}