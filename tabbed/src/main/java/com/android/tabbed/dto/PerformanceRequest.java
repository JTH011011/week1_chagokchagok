package com.android.tabbed.dto;

import java.time.LocalDateTime;

public class PerformanceRequest {
    private String userId;
    private String name;
    private String genre;
    private String cast;  // Request에서 사용하는 필드명
    private String castInfo; // Entity에 매핑할 필드명
    private LocalDateTime attendingDate;
    private String seat;
    private Integer price;

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public String getCast() { return cast; }
    public void setCast(String cast) { this.cast = cast; }
    
    public String getCastInfo() { return cast != null ? cast : castInfo; }  // cast媛� �엳�쑝硫� cast, �뾾�쑝硫� castInfo �궗�슜
    public void setCastInfo(String castInfo) { this.castInfo = castInfo; }
    
    public LocalDateTime getAttendingDate() { return attendingDate; }
    public void setAttendingDate(LocalDateTime attendingDate) { this.attendingDate = attendingDate; }
    
    public String getSeat() { return seat; }
    public void setSeat(String seat) { this.seat = seat; }
    
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
}
