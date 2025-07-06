package com.android.tabbed.repository

import com.android.tabbed.entity.Upload
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UploadRepository : JpaRepository<Upload, Long>
