package com.android.tabbed.repository;

import com.android.tabbed.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findByUserId(String userId);
    List<Performance> findByUserIdAndAttendingDateBetween(String userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query(
            value = """
    		    SELECT * FROM performances
    		    WHERE user_id = :userId
    		      AND FORMATDATETIME(attending_date, 'yyyyMM') = :yearMonth
    		  """,
            nativeQuery = true
    )
    List<Performance> findByUserIdAndYearMonth(
            @Param("userId") String userId,
            @Param("yearMonth") String yearMonth  // 예: "202507"
    );
}