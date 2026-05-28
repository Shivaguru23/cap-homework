package com.capgemini.caphomework.dto;

import com.capgemini.caphomework.entity.TodoStatus;

import java.time.LocalDateTime;

public class TodoResponse {

    private Long id;
    private String title;
    private TodoStatus status;
    private LocalDateTime createdAt;

    public TodoResponse(Long id,
                        String title,
                        TodoStatus status,
                        LocalDateTime createdAt) {

        this.id = id;
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}