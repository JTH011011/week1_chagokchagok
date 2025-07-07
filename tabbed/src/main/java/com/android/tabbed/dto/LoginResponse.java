package com.android.tabbed.dto;

public class LoginResponse {
    private String userId;
    private String username;
    private String name;

    public LoginResponse(String userId, String username, String name) {
        this.userId = userId;
        this.username = username;
        this.name = name;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
