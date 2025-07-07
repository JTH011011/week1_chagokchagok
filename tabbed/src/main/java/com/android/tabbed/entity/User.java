package com.android.tabbed.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String passwordHash;

    @Column(length = 20)
    private String name;



    // 기본 생성자
    public User() {}

    // 생성자
    public User(String userId, String passwordHash, String name) {
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.name = name;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
