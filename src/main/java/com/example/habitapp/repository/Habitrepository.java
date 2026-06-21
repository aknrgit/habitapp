package com.example.habitapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitapp.entity.Habit;

public interface Habitrepository extends JpaRepository<Habit,Long>{
	List<Habit> findByOwnerUserId(Long ownerUserId);
}
