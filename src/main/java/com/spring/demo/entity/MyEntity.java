package com.spring.demo.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "my_table")
@TypeDefs({@TypeDef(name = "jsonb",typeClass = JsonBinaryType.class)})
public class MyEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String , Object> dataSchema ;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String,Object> routerConfig;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public MyEntity() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }


    public enum StatusEnum{
        Live,Draft,Retired
    }

    public MyEntity(UUID id, Map<String, Object> dataSchema, Map<String, Object> routerConfig, StatusEnum status, String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.dataSchema = dataSchema;
        this.routerConfig = routerConfig;
        this.status = status;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Map<String, Object> getDataSchema() {
        return dataSchema;
    }

    public void setDataSchema(Map<String, Object> dataSchema) {
        this.dataSchema = dataSchema;
    }

    public Map<String, Object> getRouterConfig() {
        return routerConfig;
    }

    public void setRouterConfig(Map<String, Object> routerConfig) {
        this.routerConfig = routerConfig;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

}
