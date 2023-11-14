package com.spring.demo.repository;

import com.spring.demo.entity.MyEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyEntityRepositoryTest {
    @Autowired
    private MyEntityRepository entityRepository;

    @Test
    void existsById() {
        UUID uuid=UUID.randomUUID();
        Map<String,Object>dataSchema=new HashMap<>();
        dataSchema.put("name","Rahul");
        Map<String,Object>routerConfig=new HashMap<>();
        routerConfig.put("speed","10gb");
        Enum anEnum=MyEntity.StatusEnum.Live;


        MyEntity entity=new MyEntity();
        entity.setId(uuid);
        entity.setCreatedBy("user1");
        entity.setUpdatedBy("user2");
        entity.setDataSchema(dataSchema);
        entity.setRouterConfig(routerConfig);
        entity.setStatus((MyEntity.StatusEnum) anEnum);

        entityRepository.save(entity);

        boolean expRes= entityRepository.existsById(uuid);

        assertTrue(expRes,"Entity  exist with the specified ID ");

    }

}
