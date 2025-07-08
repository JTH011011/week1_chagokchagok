package com.android.tabbed.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Base64;

@NoArgsConstructor
@AllArgsConstructor
public class PerformanceResponseDTO {
    private Long performanceId;
    private LocalDateTime attendingDate;
    private String castInfo;
    private String genre;
    private String name;
    private String photo;  // base64 인코딩된 사진 데이터
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

        // 사진 파일을 base64로 인코딩
        if (performance.getPhotoPath() != null) {
            try {
                File photoFile = new File(performance.getPhotoPath());
                if (photoFile.exists() && photoFile.isFile()) {
                    byte[] photoBytes = Files.readAllBytes(photoFile.toPath());
                    String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
                    // 파일 확장자로 MIME 타입 결정
                    String fileName = photoFile.getName();
                    String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
                    String mimeType = "image/jpeg"; // 기본값

                    switch (fileExtension) {
                        case "jpg":
                        case "jpeg":
                            mimeType = "image/jpeg";
                            break;
                        case "png":
                            mimeType = "image/png";
                            break;
                        case "gif":
                            mimeType = "image/gif";
                            break;
                    }

                    dto.setPhoto("data:" + mimeType + ";base64," + base64Photo);
                } else {
                    dto.setPhoto(null);  // 파일이 없거나 디렉토리일 경우 null 처리
                }
            } catch (Exception e) {
                dto.setPhoto(null);  // 읽기 실패 시 null 처리
                e.printStackTrace();  // 에러 로깅
            }
        } else {
            dto.setPhoto(null);  // 경로가 없을 경우 null 처리
        }

        return dto;
    }
}
