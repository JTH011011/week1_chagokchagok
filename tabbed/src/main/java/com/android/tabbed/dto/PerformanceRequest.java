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
    private int price;
    private String photoUrl; // 이미지 URL

    public PerformanceRequest() {}

    public PerformanceRequest(String userId, String name, String genre, String castInfo, LocalDateTime attendingDate, String seat, int price, String photoUrl) {
        this.userId = userId;
        this.name = name;
        this.genre = genre;
        this.castInfo = castInfo;
        this.attendingDate = attendingDate;
        this.seat = seat;
        this.price = price;
        this.photoUrl = photoUrl;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getCastInfo() { return castInfo; }
    public void setCastInfo(String castInfo) { this.castInfo = castInfo; }

    public LocalDateTime getAttendingDate() { return attendingDate; }
    public void setAttendingDate(LocalDateTime attendingDate) { this.attendingDate = attendingDate; }

    public String getSeat() { return seat; }
    public void setSeat(String seat) { this.seat = seat; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getCast() { return cast; }
    public void setCast(String cast) { this.cast = cast; }


}