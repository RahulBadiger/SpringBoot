package com.spring.demo.repository;

import com.spring.demo.entity.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MyEntityRepository extends JpaRepository<MyEntity, UUID> {
    boolean existsById(UUID id);

    void deleteById(UUID id);

    Optional<MyEntity> findById(UUID id);
}
