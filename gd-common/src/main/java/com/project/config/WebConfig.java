package com.project.config;

import com.project.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;

    /**
     * 请求拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，并排除登录和注册接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        "/doc.html",
                        "/doc.html/**",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/static/**",
                        "/public/**"
                ); // 排除登录、注册和 Knife4j 相关路径
    }

    /**
     * 跨域拦截
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 放行哪些域名（使用具体的域名，避免和 allowCredentials 冲突）
                .allowedOriginPatterns("*") // 允许的前端域名
                // 允许的请求头
                .allowedHeaders("Authorization", "Content-Type") // 明确列出需要的请求头
                // 允许的请求方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 是否允许携带凭证（如 cookies）
                .allowCredentials(true)
                // 预检请求缓存时间（单位：秒）
                .maxAge(3600);
    }
}
