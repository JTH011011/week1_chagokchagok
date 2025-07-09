package com.android.tabbed.service;

import com.android.tabbed.dto.BudgetRequest;
import com.android.tabbed.dto.BudgetResponse;
import com.android.tabbed.entity.Budget;
import com.android.tabbed.repository.BudgetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BudgetServiceImpl implements BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public BudgetResponse createBudget(String userId, YearMonth month,BudgetRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Budget request cannot be null");
        }

        if (userId == null || month == null|| request.getBudget() == null) {
            throw new IllegalArgumentException("userId, yearMonth, and budget are required fields");
        }

        Budget budget = new Budget();
        budget.setUserId(userId);
        budget.setYearMonth(month);
        budget.setBudget(request.getBudget());
        budget.setSpending(0);
        budget.setRemaining(request.getBudget());
        Budget savedBudget = budgetRepository.save(budget);

        BudgetResponse response = new BudgetResponse();
        response.setId(savedBudget.getId());
        response.setUserId(savedBudget.getUserId());
        response.setYearMonth(savedBudget.getYearMonth());
        response.setBudget(savedBudget.getBudget());
        response.setSpending(savedBudget.getSpending());
        response.setRemaining(savedBudget.getRemaining());

        return response;
    }

    @Override
    public BudgetResponse getBudget(Long id) {
        Optional<Budget> optionalBudget = budgetRepository.findById(id);
        return optionalBudget.map(this::toResponse).orElseThrow(() ->
                new RuntimeException("Budget not found: " + id));
    }

    @Override
    public List<BudgetResponse> getBudgetsByUser(String userId) {
        return budgetRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetResponse getBudgetByMonth(String userId, YearMonth yearMonth) {
        Optional<Budget> optionalBudget = budgetRepository.findByUserIdAndYearMonth(userId, yearMonth);
        return optionalBudget.map(this::toResponse).orElseThrow(() ->
                new RuntimeException("Budget not found for user " + userId + " and month " + yearMonth));
    }

    @Override
    public BudgetResponse updateBudget(Long id, BudgetRequest request) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found: " + id));

        budget.setUserId(String.valueOf(request.getUserId()));
        budget.setYearMonth(request.getYearMonth());
        budget.setBudget(request.getBudget());
        budget.setSpending(request.getSpending());
        budget.setRemaining(request.getBudget() - request.getSpending());

        return toResponse(budgetRepository.save(budget));
    }


    @Override
    public BudgetResponse updateBudgetByMonth(String userId, YearMonth yearMonth, BudgetRequest request) {
        Budget budget = budgetRepository.findByUserIdAndYearMonth(userId, yearMonth)
                .orElseThrow(() -> new RuntimeException("Budget not found for user " + userId + " and month " + yearMonth));

        budget.setBudget(request.getBudget());
        budget.setSpending(request.getSpending());
        budget.setRemaining(request.getBudget() - request.getSpending());

        return toResponse(budgetRepository.save(budget));
    }

    @Override
    public void deleteBudget(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found: " + id));
        budgetRepository.delete(budget);
    }

    private BudgetResponse toResponse(Budget budget) {
        BudgetResponse response = new BudgetResponse();
        response.setId(budget.getId());
        response.setUserId(budget.getUserId());
        response.setYearMonth(budget.getYearMonth());
        response.setBudget(budget.getBudget());
        response.setSpending(budget.getSpending());
        response.setRemaining(budget.getRemaining());
        return response;
    }
}
