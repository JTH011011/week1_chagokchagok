package com.android.tabbed.entity;

import jakarta.persistence.*;
import java.time.YearMonth;

@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "year_month")
    private YearMonth yearMonth;

    private Integer budget;
    private Integer spending = 0; // 기본값으로 0 설정
    private Integer remaining;

    // 기본 생성자
    public Budget() {}

    // 생성자
    public Budget(String userId, YearMonth yearMonth, Integer budget, Integer spending, Integer remaining) {
        this.userId = userId;
        this.yearMonth = yearMonth;
        this.budget = budget;
        this.spending = spending != null ? spending : 0; // null�씠硫� 0�쑝濡� �꽕�젙
        this.spending = spending;
        this.remaining = remaining;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public YearMonth getYearMonth() { return yearMonth; }
    public void setYearMonth(YearMonth yearMonth) { this.yearMonth = yearMonth; }

    public Integer getBudget() { return budget; }
    public void setBudget(Integer budget) { this.budget = budget; }

    public Integer getSpending() { return spending; }
    public void setSpending(Integer spending) {
        this.spending = spending;
        updateRemaining();
    }

    public Integer getRemaining() { return remaining; }
    public void setRemaining(Integer remaining) { this.remaining = remaining; }

    private void updateRemaining() {
        if (budget != null && spending != null) {
            this.remaining = budget - spending;
        }
    }
}
