package com.android.tabbed.controller;

import com.android.tabbed.dto.PerformanceRequest;
import com.android.tabbed.dto.PerformanceResponseDTO;
import com.android.tabbed.entity.Performance;
import com.android.tabbed.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/performance")
public class PerformanceController {
    @Autowired
    private PerformanceService performanceService;

    @PostMapping
    public ResponseEntity<PerformanceResponseDTO> createPerformance(@RequestBody PerformanceRequest request) {
        // Performance 엔티티 생성
        Performance performance = new Performance();
        performance.setUserId(request.getUserId());
        performance.setName(request.getName());
        performance.setGenre(request.getGenre());
        performance.setCastInfo(request.getCastInfo());
        performance.setAttendingDate(request.getAttendingDate());
        performance.setSeat(request.getSeat());
        performance.setPrice(request.getPrice());

        try {
            // 사진 저장
            if (request.getPhoto() != null) {
                // base64 디코딩
                byte[] photoBytes = Base64.getDecoder().decode(request.getPhoto());

                // 파일명 생성: uuid_타임스탬프.jpg
                String fileName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis();
                String fileExtension = "jpg"; // 기본값

                // base64 데이터에서 MIME 타입 추출
                if (request.getPhoto().startsWith("data:")) {
                    int commaIndex = request.getPhoto().indexOf(",");
                    if (commaIndex > 0) {
                        String mimeType = request.getPhoto().substring(5, commaIndex);
                        // MIME 타입에 따라 확장자 결정
                        if (mimeType.contains("jpeg") || mimeType.contains("jpg")) {
                            fileExtension = "jpg";
                        } else if (mimeType.contains("png")) {
                            fileExtension = "png";
                        } else if (mimeType.contains("gif")) {
                            fileExtension = "gif";
                        }
                    }
                }
                fileName += "." + fileExtension;

                // uploads 디렉토리에 저장
                String uploadDir = "uploads";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File file = new File(uploadDir + File.separator + fileName);
                Files.write(file.toPath(), photoBytes);

                // 파일 경로 설정
                request.setPhotoPath(file.getAbsolutePath());
                performance.setPhotoPath(request.getPhotoPath());
            }

            Performance createdPerformance = performanceService.createPerformance(performance);
            PerformanceResponseDTO response = PerformanceResponseDTO.fromEntity(createdPerformance);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceResponseDTO> getPerformance(@PathVariable("id") Long id) {
        Performance performance = performanceService.getPerformance(id);
        PerformanceResponseDTO responseDTO = PerformanceResponseDTO.fromEntity(performance);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PerformanceResponseDTO>> getAllPerformances() {
        List<Performance> performances = performanceService.getAllPerformances();
        List<PerformanceResponseDTO> dtos = performances.stream()
                .map(PerformanceResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/user/{userId}/month/{yearMonth}")
    public ResponseEntity<List<PerformanceResponseDTO>> getPerformancesByUserAndMonth(
            @PathVariable("userId") String userId,
            @PathVariable("yearMonth") String yearMonth) {
        List<Performance> performances = performanceService.getPerformancesByUserAndYearMonth(userId, yearMonth);
        List<PerformanceResponseDTO> dtos = performances.stream()
                .map(PerformanceResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PerformanceResponseDTO>> getPerformancesByUser(@PathVariable("userId") String userId) {
        List<Performance> performances = performanceService.getPerformancesByUser(userId);
        List<PerformanceResponseDTO> dtos = performances.stream()
                .map(PerformanceResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

}