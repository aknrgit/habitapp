package com.example.habitapp.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitapp.entity.HabitRecord;

public interface HabitRecordRepository extends JpaRepository<HabitRecord, Long> {
    boolean existsByHabitIdAndAchievedDate(Long habitId, LocalDate achievedDate);

}

