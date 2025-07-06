package com.android.tabbed.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "performances")
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "performance_id")
    private Long performanceId;

    @Column(name = "user_id")
    private String userId;

    @Column(length = 40)
    private String name;

    @Column(length = 20)
    private String genre;

    @Column(name = "cast_info", length = 40)
    private String castInfo;

    @Column(name = "attending_date")
    private LocalDateTime attendingDate;

    @Column(length = 20)
    private String seat;

    private Integer price;

    // 기본 생성자
    public Performance() {}

    // 생성자
    public Performance(String name, String genre, String castInfo, LocalDateTime attendingDate, String seat, Integer price) {
        this.name = name;
        this.genre = genre;
        this.castInfo = castInfo;
        this.attendingDate = attendingDate;
        this.seat = seat;
        this.price = price;
    }

    // Getters and Setters
    public Long getPerformanceId() { return performanceId; }
    public void setPerformanceId(Long performanceId) { this.performanceId = performanceId; }

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

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
}
