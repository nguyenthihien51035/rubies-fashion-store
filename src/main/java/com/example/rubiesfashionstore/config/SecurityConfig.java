package com.example.rubiesfashionstore.config;

import com.example.rubiesfashionstore.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final CustomUserDetailsServiceImpl customUserDetailsService;

    public SecurityConfig(CustomUserDetailsServiceImpl customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() //
                )
                .build(); //
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        // Permit các endpoint không cần login
//                        .requestMatchers(
//                                "/api/v1/users/login",
//                                "/api/v1/users/register"
//                        ).permitAll()
//
//                        // Permit GET các danh mục, sản phẩm, màu sắc
//                        .requestMatchers(HttpMethod.GET,
//                                "/api/v1/products/**",
//                                "/api/v1/categories/**",
//                                "/api/v1/colors/**"
//                        ).permitAll()
//
//                        .requestMatchers(HttpMethod.POST,
//                                "/api/v1/products/filter",
//                                "/api/v1/users/forgot-password"
//                        ).permitAll()
//
//                        .requestMatchers(HttpMethod.PUT,
//                                "/api/v1/users/reset-password"
//                        ).permitAll()
//
//                        // ADMIN thêm, sửa, xóa
//                        .requestMatchers(HttpMethod.PUT,
//                                "/api/v1/products/**",
//                                "/api/v1/colors/**",
//                                "/api/v1/categories/**"
//                        ).hasRole("ADMIN")
//
//                        .requestMatchers(HttpMethod.DELETE,
//                                "/api/v1/products/**",
//                                "/api/v1/colors/**",
//                                "/api/v1/categories/**"
//                        ).hasRole("ADMIN")
//
//                        // Còn lại cần login
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(httpBasic -> {
//                })
//                .userDetailsService(customUserDetailsService)
//                .build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
