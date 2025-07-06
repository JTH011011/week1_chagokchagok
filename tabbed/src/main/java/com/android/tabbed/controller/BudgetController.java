package com.android.tabbed.controller;

import com.android.tabbed.dto.BudgetRequest;
import com.android.tabbed.dto.BudgetResponse;
import com.android.tabbed.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/budget")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public ResponseEntity<Long> createBudget(@RequestBody BudgetRequest request) {
        Long budgetId = budgetService.createBudget(request);
        return ResponseEntity.ok(budgetId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> getBudget(@PathVariable("id") Long id) {
        BudgetResponse budget = budgetService.getBudget(id);
        return ResponseEntity.ok(budget);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BudgetResponse>> getBudgetsByUser(@PathVariable("userId") String userId) {
        List<BudgetResponse> budgets = budgetService.getBudgetsByUser(userId);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/user/{userId}/month/{yearMonth}")
    public ResponseEntity<BudgetResponse> getBudgetByMonth(
            @PathVariable("userId") String userId,
            @PathVariable("yearMonth") String yearMonth) {
        YearMonth month = YearMonth.parse(yearMonth);
        BudgetResponse budget = budgetService.getBudgetByMonth(userId, month);
        return ResponseEntity.ok(budget);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> updateBudget(
            @PathVariable("id") Long id,
            @RequestBody BudgetRequest request) {
        BudgetResponse budget = budgetService.updateBudget(id, request);
        return ResponseEntity.ok(budget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.ok().build();
    }
}
