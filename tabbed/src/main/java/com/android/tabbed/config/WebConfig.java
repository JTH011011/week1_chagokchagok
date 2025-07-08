package com.android.tabbed.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploads/**")  // 요청 URL
                .addResourceLocations("file:/Users/taeheejeong/Desktop/chagokchagok/tabbed/uploads/"); // 실제 물리 경로
    }
}