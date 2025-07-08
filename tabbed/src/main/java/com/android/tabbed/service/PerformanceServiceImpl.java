package com.android.tabbed.service;

import com.android.tabbed.entity.Performance;
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
    public Performance createPerformance(Performance performance) {
        Performance savedPerformance = performanceRepository.save(performance);
        updateBudgetForPerformance(savedPerformance);
        return savedPerformance;
    }

    @Override
    public void updateBudgetForPerformance(Performance performance) {
        String userId = performance.getUserId();
        YearMonth month = YearMonth.from(performance.getAttendingDate());
        Integer price = performance.getPrice();

        Optional<Budget> budgetOpt = budgetRepository.findByUserIdAndYearMonth(userId, month);
        if (budgetOpt.isPresent()) {
            // 기존 예산이 있는 경우
            Budget budget = budgetOpt.get();
            Integer currentSpending = budget.getSpending();
            Integer newSpending = currentSpending + price;
            Integer remaining = budget.getBudget() - newSpending;

            budget.setSpending(newSpending);
            budget.setRemaining(remaining);
            budgetRepository.save(budget);
        } else {
            // 새로운 예산 생성
            Budget budget = new Budget();
            budget.setUserId(userId);
            budget.setYearMonth(month);
            budget.setBudget(0); // 기본 예산은 0으로 설정
            budget.setSpending(price);
            budget.setRemaining(0 - price);
            budgetRepository.save(budget);
        }
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

    @Override
    public List<Performance> getPerformancesByUserAndYearMonth(String userId, String yearMonth) {
        return performanceRepository.findByUserIdAndYearMonth(userId, yearMonth);
    }
}