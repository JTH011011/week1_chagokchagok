package com.android.tabbed.dto;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

public class PerformanceRequest {
    private String userId;
    private String name;
    private String genre;
    private String cast;  // Request에서 사용하는 필드명
    private String castInfo; // Entity에 매핑할 필드명
    private LocalDateTime attendingDate;
    private String seat;
    private int price;
    private String photo; // base64 인코딩된 문자열
    private String photoPath; // 파일 저장 경로

    public PerformanceRequest() {}

    public PerformanceRequest(String userId, String name, String genre, String castInfo, LocalDateTime attendingDate, String seat, int price, String photo) {
        this.userId = userId;
        this.name = name;
        this.genre = genre;
        this.castInfo = castInfo;
        this.attendingDate = attendingDate;
        this.seat = seat;
        this.price = price;
        this.photo = photo;
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

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) {
        this.photo = photo;
        // base64 데이터에서 MIME 타입 추출
        if (photo != null && photo.startsWith("data:")) {
            int commaIndex = photo.indexOf(",");
            if (commaIndex > 0) {
                String mimeType = photo.substring(5, commaIndex);
                // 순수한 base64 데이터만 저장
                this.photo = photo.substring(commaIndex + 1);
            }
        }
    }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public String getCast() { return cast; }
    public void setCast(String cast) { this.cast = cast; }

    /**
     * Base64 인코딩된 사진 데이터를 파일로 저장하고 파일 경로를 반환
     */
    public String savePhoto() {
        if (photo == null || photo.isEmpty()) {
            return null;
        }

        try {
            // Base64 디코딩
            byte[] decodedBytes = Base64.getDecoder().decode(photo);

            // 파일명 생성 (UUID + 타임스탬프 + 확장자)
            String fileName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + ".jpg";

            // 파일 저장 경로
            String uploadDir = "uploads/";
            String filePath = uploadDir + fileName;

            // 디렉토리 생성
            java.io.File uploadDirFile = new java.io.File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            // 파일 저장
            java.io.File file = new java.io.File(filePath);
            java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
            fos.write(decodedBytes);
            fos.close();

            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}