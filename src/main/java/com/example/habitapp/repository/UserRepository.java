package com.example.habitapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginId(String loginId);

    User findByFriendCode(String friendCode);
}