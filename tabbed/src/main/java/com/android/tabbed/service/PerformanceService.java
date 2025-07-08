package com.android.tabbed.service;

import com.android.tabbed.entity.Performance;
import java.util.List;

public interface PerformanceService {
    Performance getPerformanceByPhotoUrl(String photoUrl);
    List<String> getPhotoUrlsByYearMonth(int year, int month);
    List<String> getPhotoUrlsByUser(String userId);
    Performance createPerformance(Performance performance);
    void updateBudgetForPerformance(Performance performance);
    Performance getPerformance(Long id);
    List<Performance> getAllPerformances();
    Performance updatePerformance(Long id, Performance performance);
    void deletePerformance(Long id);
    List<Performance> getPerformancesByUser(String userId);
    List<Performance> getPerformancesByUserAndYearMonth(String userId, String yearMonth);
}