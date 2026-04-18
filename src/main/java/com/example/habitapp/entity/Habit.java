package com.example.habitapp.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "habits")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    public Habit() {}

    public Habit(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
         return id; 
    }
    public String getTitle() { 
        return title; 
    }
    public String getDescription() {
         return description; 
    }

    public void setTitle(String title) { 
        this.title = title; 
    }
    public void setDescription(String description) {
         this.description = description; 
    }
}