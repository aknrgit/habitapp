package com.example.habitapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitapp.entity.HabitRecord;

public interface HabitRecordRepository extends JpaRepository<HabitRecord, Long> {
    boolean existsByHabitIdAndAchievedDate(Long habitId, LocalDate achievedDate);
    int countByHabitIdAndAchievedDateBetween(Long habitId,LocalDate start,LocalDate end);

    //今日の日付を取得する。
    List<HabitRecord> findByAchievedDate(LocalDate achievedDate);
    //日付ごとにどんな習慣をやったか取得する。
    List<HabitRecord> findAllByOrderByAchievedDateDesc();

}

