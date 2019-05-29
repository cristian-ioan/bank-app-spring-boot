package com.banking.dto;

import java.time.LocalDateTime;

public class UserDTO {

    private Long id;
    private String name;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public UserDTO(Long id, String name, LocalDateTime createdTime, LocalDateTime updatedTime) {
        super();
        this.id = id;
        this.name = name;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
