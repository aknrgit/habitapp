package com.example.habitapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitapp.entity.Habit;

public interface Habitrepository extends JpaRepository<Habit,Long>{
    
}
