package com.example.habitapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitapp.entity.DailySchedule;

public interface DailyScheduleRepository extends JpaRepository<DailySchedule, Long> {
    List<DailySchedule> findAllByOrderByScheduleTimeAsc();
}