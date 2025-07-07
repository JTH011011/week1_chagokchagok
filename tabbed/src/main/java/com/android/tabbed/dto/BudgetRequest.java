package com.android.tabbed.dto;

import java.time.YearMonth;

public class BudgetRequest {
    private String userId;
    private YearMonth yearMonth;
    private Integer budget;
    private Integer spending = 0; // 기본값으로 0 설정

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getSpending() {
        return spending;
    }

    public void setSpending(Integer spending) {
        this.spending = spending;
    }
}
