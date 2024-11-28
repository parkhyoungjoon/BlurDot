package com.mysite.blurdot.config;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FileConfig {
    @Bean
    public String uploadDir() {
        // 애플리케이션 실행 디렉터리 + /uploads
        String uploadPath = System.getProperty("user.dir") + "/uploads";
        File uploadFolder = new File(uploadPath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs(); // 폴더가 없으면 생성
        }
        return uploadPath;
    }
}