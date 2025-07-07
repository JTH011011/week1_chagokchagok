package com.android.tabbed.service

import com.android.tabbed.entity.Upload
import com.android.tabbed.repository.UploadRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
interface UploadService {
    fun saveUpload(content: String, description: String): Upload
}

@Service
class UploadServiceImpl(
    private val uploadRepository: UploadRepository
) : UploadService {
    override fun saveUpload(content: String, description: String): Upload {
        val upload = Upload(content = content, description = description)
        return uploadRepository.save(upload)
    }
}
