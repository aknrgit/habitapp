package com.example.habitapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitapp.entity.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByOwnerUserId(Long ownerUserId);

    boolean existsByOwnerUserIdAndFriendUserId(Long ownerUserId, Long friendUserId);
}