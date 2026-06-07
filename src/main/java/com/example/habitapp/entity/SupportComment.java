package com.example.habitapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SupportComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String supporterName;

    private String message;

    private LocalDateTime createdAt;

    private Long targetUserId;
    

    public SupportComment() {
    }

    public SupportComment(String supporterName, String message, LocalDateTime createdAt) {
        this.supporterName = supporterName;
        this.message = message;
        this.createdAt = createdAt;
    }

    public SupportComment(String supporterName, String message, LocalDateTime createdAt, Long targetUserId) {
        this.supporterName = supporterName;
        this.message = message;
        this.createdAt = createdAt;
        this.targetUserId = targetUserId;
    }

    public Long getId() {
        return id;
    }

    public String getSupporterName() {
        return supporterName;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
}