package com.android.tabbed.service;

import com.android.tabbed.entity.Performance;
import com.android.tabbed.dto.PerformanceResponse;
import com.android.tabbed.entity.Budget;
import com.android.tabbed.repository.PerformanceRepository;
import com.android.tabbed.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PerformanceServiceImpl implements PerformanceService {
    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public PerformanceResponse createPerformance(Performance performance) {
        Performance savedPerformance = performanceRepository.save(performance);
        String warningMessage = updateBudgetForPerformance(savedPerformance);
        return new PerformanceResponse(savedPerformance, warningMessage);
    }

    public String updateBudgetForPerformance(Performance performance) {
        String userId = performance.getUserId();
        YearMonth month = YearMonth.from(performance.getAttendingDate());
        Integer price = performance.getPrice();

        Optional<Budget> budgetOpt = budgetRepository.findByUserIdAndYearMonth(userId, month);
        if (budgetOpt.isEmpty() || budgetOpt.get().getBudget() == null || budgetOpt.get().getBudget() == 0) {
            throw new RuntimeException("예산을 먼저 등록하세요.");
        }

        Budget budget = budgetOpt.get();
        Integer newSpending = budget.getSpending() + price;
        Integer remaining = budget.getBudget() - newSpending;
        budget.setSpending(newSpending);
        budget.setRemaining(remaining);
        budgetRepository.save(budget);

        if (newSpending > budget.getBudget()) {
            return "경고: 예산을 초과했습니다. 예산: " + budget.getBudget() + ", 총 지출: " + newSpending;
        }
        return null;
    }

    @Override
    public Performance getPerformance(Long id) {
        Optional<Performance> optionalPerformance = performanceRepository.findById(id);
        return optionalPerformance.orElseThrow(() -> new RuntimeException("Performance not found"));
    }

    @Override
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    @Override
    public Performance updatePerformance(Long id, Performance performance) {
        Performance existingPerformance = getPerformance(id);
        existingPerformance.setName(performance.getName());
        existingPerformance.setGenre(performance.getGenre());
        existingPerformance.setCastInfo(performance.getCastInfo());
        existingPerformance.setAttendingDate(performance.getAttendingDate());
        existingPerformance.setSeat(performance.getSeat());
        existingPerformance.setPrice(performance.getPrice());
        return performanceRepository.save(existingPerformance);
    }

    @Override
    public void deletePerformance(Long id) {
        Performance performance = getPerformance(id);
        performanceRepository.delete(performance);
    }

    @Override
    public List<Performance> getPerformancesByUser(String userId) {
        return performanceRepository.findByUserId(userId);
    }
}

