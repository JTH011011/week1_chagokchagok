package com.android.tabbed.service;

import com.android.tabbed.entity.Photo;
import com.android.tabbed.entity.Performance;
import java.time.LocalDateTime;
import java.util.List;

public interface GetPhotoService {
    List<Photo> findAll();
    List<Photo> findPhotosByPerformance(Performance performance);
    List<Performance> findPerformancesByUserAndMonth(String userId, LocalDateTime startDate, LocalDateTime endDate);
}
