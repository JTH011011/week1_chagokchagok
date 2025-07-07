package com.android.tabbed.service;

import com.android.tabbed.dto.PerformanceResponse;
import com.android.tabbed.entity.Performance;
import java.util.List;

public interface PerformanceService {
    PerformanceResponse createPerformance(Performance performance);
    String updateBudgetForPerformance(Performance performance);
    Performance getPerformance(Long id);
    List<Performance> getAllPerformances();
    Performance updatePerformance(Long id, Performance performance);
    void deletePerformance(Long id);
    List<Performance> getPerformancesByUser(String userId);
}
