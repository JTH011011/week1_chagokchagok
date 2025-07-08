package com.android.tabbed.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class PerformanceResponseDTO {
    private Long performanceId;
    private LocalDateTime attendingDate;
    private String castInfo;
    private String genre;
    private String name;
    private String photoUrl;  // 이미지 URL
    private Integer price;
    private String seat;
    private String userId;

    // Getter & Setter
    public Long getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(Long performanceId) {
        this.performanceId = performanceId;
    }

    public LocalDateTime getAttendingDate() {
        return attendingDate;
    }

    public void setAttendingDate(LocalDateTime attendingDate) {
        this.attendingDate = attendingDate;
    }

    public String getCastInfo() {
        return castInfo;
    }

    public void setCastInfo(String castInfo) {
        this.castInfo = castInfo;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static PerformanceResponseDTO fromEntity(com.android.tabbed.entity.Performance performance) {
        PerformanceResponseDTO dto = new PerformanceResponseDTO();
        dto.setPerformanceId(performance.getPerformanceId());
        dto.setAttendingDate(performance.getAttendingDate());
        dto.setCastInfo(performance.getCastInfo());
        dto.setGenre(performance.getGenre());
        dto.setName(performance.getName());
        dto.setPrice(performance.getPrice());
        dto.setSeat(performance.getSeat());
        dto.setUserId(performance.getUserId());
        dto.setPhotoUrl(performance.getPhotoUrl());
        return dto;
    }
}
