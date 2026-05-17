package com.example.habitapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitapp.entity.DailyComment;

public interface DailyCommentRepository extends JpaRepository<DailyComment,Long> {
    List<DailyComment> findAllByOrderByCreatedDateDesc();
}