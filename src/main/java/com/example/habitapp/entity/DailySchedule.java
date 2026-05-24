package com.example.habitapp.entity;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DailySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime scheduleTime;

    private String content;

    private String scheduleType;

    public DailySchedule() {

    }

    public DailySchedule(LocalTime scheduleTime,String content,String scheduleType) {
        this.scheduleTime = scheduleTime;
        this.content = content;
        this.scheduleType = scheduleType;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getScheduleTime() {
        return scheduleTime;
    }

    public String getContent() {
        return content;
    }
    public String getScheduleType() {
        return scheduleType;
    }
}