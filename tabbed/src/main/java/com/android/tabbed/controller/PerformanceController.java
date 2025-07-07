package com.android.tabbed.controller;

import com.android.tabbed.dto.PerformanceRequest;
import com.android.tabbed.entity.Performance;
import com.android.tabbed.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;
import com.android.tabbed.dto.PerformanceResponse;

@RestController
@RequestMapping("/performance")
public class PerformanceController {
    @Autowired
    private PerformanceService performanceService;

    @PostMapping
    public ResponseEntity<PerformanceResponse> createPerformance(@RequestBody PerformanceRequest request) {
        Performance performance = new Performance();
        performance.setUserId(request.getUserId());
        performance.setName(request.getName());
        performance.setGenre(request.getGenre());
        performance.setCastInfo(request.getCast());  // cast 필드의 값을 가져와서 castInfo로 설정
        performance.setAttendingDate(request.getAttendingDate());
        performance.setSeat(request.getSeat());
        performance.setPrice(request.getPrice());
        
        PerformanceResponse response = performanceService.createPerformance(performance);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Performance> getPerformance(@PathVariable Long id) {
        Performance performance = performanceService.getPerformance(id);
        return ResponseEntity.ok(performance);
    }

    @GetMapping
    public ResponseEntity<List<Performance>> getAllPerformances() {
        List<Performance> performances = performanceService.getAllPerformances();
        return ResponseEntity.ok(performances);
    }

    @GetMapping("/user/{userId}")
    public Mono<List<Performance>> getPerformancesByUser(@PathVariable("userId") String userId) {
        return Mono.just(performanceService.getPerformancesByUser(userId));
    }
}
