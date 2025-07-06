package com.android.tabbed.service;

import com.android.tabbed.entity.Photo;
import com.android.tabbed.repository.PhotoRepository;
import com.android.tabbed.entity.Performance;
import com.android.tabbed.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SavePhotoServiceImpl implements SavePhotoService {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private PerformanceRepository performanceRepository;

    @Override
    public Photo saveUpload(byte[] image, String description, Long performanceId) {
        Photo photo = new Photo();
        photo.setImage(image);
        photo.setDescription(description);
        
        if (performanceId != null) {
            Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new IllegalArgumentException("Performance not found"));
            photo.setPerformance(performance);
        }
        
        return photoRepository.save(photo);
    }
}
