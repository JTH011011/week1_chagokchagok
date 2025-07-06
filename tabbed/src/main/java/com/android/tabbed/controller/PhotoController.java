package com.android.tabbed.controller;

import com.android.tabbed.dto.PhotoResponse;
import com.android.tabbed.entity.Photo;
import com.android.tabbed.entity.Performance;
import com.android.tabbed.service.GetPhotoService;
import com.android.tabbed.service.SavePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    private SavePhotoService savePhotoService;

    @Autowired
    private GetPhotoService getPhotoService;

    @PostMapping("/upload")
    public ResponseEntity<Long> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam String description,
            @RequestParam(required = false) Long performanceId
    ) {
        try {
            byte[] imageBytes = file.getBytes();
            Photo photo = savePhotoService.saveUpload(imageBytes, description, performanceId);
            return ResponseEntity.ok(photo.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PhotoResponse>> getAllPhotos() {
        List<Photo> photos = getPhotoService.findAll();
        List<PhotoResponse> responses = photos.stream()
            .map(photo -> {
                PhotoResponse response = new PhotoResponse();
                response.setContent(Base64.getEncoder().encodeToString(photo.getImage()));
                response.setDescription(photo.getDescription());

                return response;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}/month/{yearMonth}")
    public ResponseEntity<List<PhotoResponse>> getPhotosByUserAndMonth(
            @PathVariable("userId") String userId,
            @PathVariable("yearMonth") String yearMonth) {
        try {
            // YYYY-MM 형식의 yearMonth를 LocalDateTime으로 변환
            int year = Integer.parseInt(yearMonth.substring(0, 4));
            int month = Integer.parseInt(yearMonth.substring(5, 7));
            
            LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);
            
            // 해당 월의 공연들 조회
            List<Performance> performances = getPhotoService.findPerformancesByUserAndMonth(userId, startDate, endDate);
            
            // 공연들에 대한 사진들 조회
            List<Photo> photos = performances.stream()
                .flatMap(performance -> getPhotoService.findPhotosByPerformance(performance).stream())
                .collect(Collectors.toList());
            
            // Response 생성
            List<PhotoResponse> responses = photos.stream()
                .map(photo -> {
                    PhotoResponse response = new PhotoResponse();
                    response.setContent(Base64.getEncoder().encodeToString(photo.getImage()));
                    response.setDescription(photo.getDescription());
                    return response;
                })
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
