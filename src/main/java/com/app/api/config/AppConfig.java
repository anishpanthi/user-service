package com.app.api.config;

import com.app.api.interceptor.ApiRequestResponseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Anish Panthi
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    private ApiRequestResponseInterceptor apiRequestResponseInterceptor;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiRequestResponseInterceptor).addPathPatterns("/v1/**");
    }

    @Autowired
    public AppConfig(ApiRequestResponseInterceptor apiRequestResponseInterceptor) {
        this.apiRequestResponseInterceptor = apiRequestResponseInterceptor;
    }
}
