package com.android.tabbed.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${upload.directory}")
    private String uploadDirectory; // application.properties에서 설정할 경로

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            // 파일 이름 생성

            System.out.println("📦 파일 이름: " + file.getOriginalFilename());
            System.out.println("📦 파일 크기: " + file.getSize());
            System.out.println("📦 isEmpty? " + file.isEmpty());
            System.out.println("📂 업로드 디렉토리: " + uploadDirectory);

            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID() + "." + extension;

            // 저장 경로
            File dest = new File(uploadDirectory + File.separator + fileName);
            dest.getParentFile().mkdirs(); // 디렉토리 생성
            file.transferTo(dest);

            // URL 구성
            String fileUrl = "http://143.248.216.95:8080/uploads/" + fileName;
            return ResponseEntity.ok().body(new ImageUrlResponse(fileUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 업로드 실패: " + e.getMessage());  // 👉 진짜 이유를 프론트로 보내자
        }
    }

    // DTO
    public static class ImageUrlResponse {
        public String url;
        public ImageUrlResponse(String url) {
            this.url = url;
        }
        public String getUrl() { return url; }
    }
}
