package com.android.tabbed.controller;

import com.android.tabbed.dto.PerformanceRequest;
import com.android.tabbed.dto.PerformanceResponseDTO;
import com.android.tabbed.entity.Performance;
import com.android.tabbed.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Performance performance = new Performance();
        performance.setUserId(request.getUserId());
        performance.setName(request.getName());
        performance.setGenre(request.getGenre());
        performance.setCastInfo(request.getCastInfo());
        performance.setAttendingDate(request.getAttendingDate());
        performance.setSeat(request.getSeat());
        performance.setPrice(request.getPrice());
        performance.setPhotoUrl(request.getPhotoUrl()); // URL만 저장

        try {
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

    @GetMapping("/byphotourl")
    public ResponseEntity<PerformanceResponseDTO> getPerformanceByPhotourl(@RequestParam("photourl") String photourl) {
        Performance performance = performanceService.getPerformanceByPhotoUrl(photourl);
        if (performance == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(PerformanceResponseDTO.fromEntity(performance));
    }

    @GetMapping("/photourls")
    public ResponseEntity<List<String>> getPhotoUrlsByYearMonth(@RequestParam("year") int year, @RequestParam("month") int month) {
        List<String> photoUrls = performanceService.getPhotoUrlsByYearMonth(year, month);
        return ResponseEntity.ok(photoUrls);
    }

    @GetMapping("/user/{userId}/photourls")
    public ResponseEntity<List<String>> getPhotoUrlsByUser(@PathVariable("userId") String userId) {
        List<String> photoUrls = performanceService.getPhotoUrlsByUser(userId);
        return ResponseEntity.ok(photoUrls);
    }

    @GetMapping
    public ResponseEntity<List<PerformanceResponseDTO>> getAllPerformances() {
        List<Performance> performances = performanceService.getAllPerformances();
        List<PerformanceResponseDTO> dtos = performances.stream()
                .map(PerformanceResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/user/{userId}/month/{yearMonth}/photourls")
    public ResponseEntity<List<PerformanceResponseDTO>> getPerformancesByUserAndMonthAndUrl(
            @PathVariable("userId") String userId,
            @PathVariable("yearMonth") String yearMonth) {
        List<Performance> performances = performanceService.getPerformancesByUserAndYearMonth(userId, yearMonth);
        List<PerformanceResponseDTO> dtos = performances.stream()
                .filter(p -> p.getPhotoUrl() != null && !p.getPhotoUrl().isBlank())
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