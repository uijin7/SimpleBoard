package com.example.simpleboard.config;

import com.example.simpleboard.web.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns(
                        "/admin/**",
                        "/posts/write",
                        "/posts/edit/**",
                        "/api/admin/**",
                        "/api/post",
                        "/api/post/update",
                        "/api/post/delete"
                );
    }
}
