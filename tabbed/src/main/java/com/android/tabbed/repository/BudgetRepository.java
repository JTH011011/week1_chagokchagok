package com.android.tabbed.repository;

import com.android.tabbed.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserIdAndYearMonth(String userId, YearMonth yearMonth);
    List<Budget> findByUserId(String userId);
}
