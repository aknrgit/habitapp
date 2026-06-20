package com.example.habitapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerUserId;   // 自分のID

    private Long friendUserId;  // 友達のID

    public Friend() {
    }

    public Friend(Long ownerUserId, Long friendUserId) {
        this.ownerUserId = ownerUserId;
        this.friendUserId = friendUserId;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public Long getFriendUserId() {
        return friendUserId;
    }
}