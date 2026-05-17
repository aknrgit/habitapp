package com.example.habitapp.entity;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DailyComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private LocalDate createdDate;

    public DailyComment() {
    }

    public DailyComment(
        String comment,
        LocalDate createdDate
    ) {

        this.comment = comment;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}
