package com.android.tabbed.service;

import com.android.tabbed.entity.Photo;

public interface SavePhotoService {
    Photo saveUpload(byte[] image, String description, Long performanceId);
}
