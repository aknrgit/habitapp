package com.example.habitapp.entity;
import java.time.LocalDate;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class HabitRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long habitId;

    private LocalDate achievedDate;

    public HabitRecord() {
    }

    public HabitRecord(Long habitId, LocalDate achievedDate) {
        this.habitId = habitId;
        this.achievedDate = achievedDate;
    }

    public Long getId() {
        return id;
    }

    public Long getHabitId() {
        return habitId;
    }

    public LocalDate getAchievedDate() {
        return achievedDate;
    }
}