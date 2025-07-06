package com.android.tabbed.service;

import java.util.List;
import com.android.tabbed.entity.Photo;
import com.android.tabbed.entity.Performance;
import com.android.tabbed.repository.PhotoRepository;
import com.android.tabbed.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class GetPhotoServiceImpl implements GetPhotoService {
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Override
    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

    @Override
    public List<Photo> findPhotosByPerformance(Performance performance) {
        return photoRepository.findByPerformanceId(performance.getPerformanceId());
    }

    @Override
    public List<Performance> findPerformancesByUserAndMonth(String userId, LocalDateTime startDate, LocalDateTime endDate) {
        return performanceRepository.findByUserIdAndAttendingDateBetween(userId, startDate, endDate);
    }
}
