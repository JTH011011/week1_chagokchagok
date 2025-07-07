package com.android.tabbed.controller

import com.android.tabbed.service.UploadService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class MainController(
    private val uploadService: UploadService
) {
    @PostMapping("/upload")
    fun upload(
        @RequestParam content: String,
        @RequestParam description: String
    ): ResponseEntity<Long> {
        val upload = uploadService.saveUpload(content, description)
        return ResponseEntity.ok(upload.id)
    }
}
