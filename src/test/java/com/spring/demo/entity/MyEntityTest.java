package com.spring.demo.entity;

import com.spring.demo.repository.MyEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meanbean.test.BeanTester;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class MyEntityTest {

    @Autowired
    MyEntityRepository entityRepository;

    @Test
    void testMyEntityConstructor(){

        UUID uuid=UUID.randomUUID();
        Map<String,Object>dataSchema=new HashMap<>();
        dataSchema.put("name","rahul");
        Map<String,Object>routerConfig=new HashMap<>();
        routerConfig.put("name","rahul");
        String createdBy="rahul";
        String updatedBy="rahul";
        MyEntity.StatusEnum statusEnum= MyEntity.StatusEnum.Retired;

        MyEntity myEntity=new MyEntity(uuid,dataSchema,routerConfig,statusEnum,createdBy,updatedBy,null,null);

        assertNotNull(myEntity);
        assertNotNull(myEntity.getCreatedDate());
        assertNotNull(myEntity.getUpdatedDate());
        assertEquals(dataSchema,myEntity.getDataSchema());
        assertEquals(routerConfig,myEntity.getRouterConfig());
        assertEquals(uuid,myEntity.getId());
        assertEquals(createdBy,myEntity.getCreatedBy());
        assertEquals(updatedBy,myEntity.getUpdatedBy());
        assertEquals(statusEnum,MyEntity.StatusEnum.Retired);





    }
}

