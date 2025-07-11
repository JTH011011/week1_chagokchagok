package com.android.tabbed.service;

import com.android.tabbed.dto.BudgetRequest;
import com.android.tabbed.dto.BudgetResponse;
import java.time.YearMonth;
import java.util.List;

public interface BudgetService {
    BudgetResponse createBudget(String userId, YearMonth yearMonth, BudgetRequest request);
    BudgetResponse getBudget(Long id);
    List<BudgetResponse> getBudgetsByUser(String userId);
    BudgetResponse getBudgetByMonth(String userId, YearMonth yearMonth);
    BudgetResponse updateBudget(Long id, BudgetRequest request);
    BudgetResponse updateBudgetByMonth(String userId, YearMonth yearMonth, BudgetRequest request);
    void deleteBudget(Long id);
}