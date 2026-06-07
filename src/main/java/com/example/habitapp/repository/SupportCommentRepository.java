package com.example.habitapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitapp.entity.SupportComment;

public interface SupportCommentRepository extends JpaRepository<SupportComment, Long> {

    List<SupportComment> findAllByOrderByCreatedAtDesc();
    List<SupportComment> findByTargetUserId(Long targetUserId);

}