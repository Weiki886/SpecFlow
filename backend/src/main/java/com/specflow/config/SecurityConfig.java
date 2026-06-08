package com.specflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Knife4j 文档相关路径全部放行
                .requestMatchers(
                    "/doc.html",
                    "/webjars/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/favicon.ico",
                    "/v3/api-docs/**",
                    "/v3/api-docs",
                    "/swagger-resources/**",
                    "/webjars/knife4j/**"
                ).permitAll()
                // 其他接口需要认证
                .anyRequest().permitAll()
            );
        return http.build();
    }
}
