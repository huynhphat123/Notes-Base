package com.tuanphat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Tắt CSRF (nếu cần)
                .csrf(csrf -> csrf.disable())

                // Cấu hình phân quyền
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").hasRole("USER") // Chỉ USER mới truy cập được
                        .requestMatchers("/**").permitAll() // Tất cả mọi người có thể truy cập
                )

                // Cấu hình đăng nhập
                .formLogin(login -> login
                        .loginPage("/signin") // Trang login tùy chỉnh
                        .loginProcessingUrl("/userLogin") // URL xử lý form login
                        .defaultSuccessUrl("/user/addNotes", true) // URL chuyển hướng sau khi login thành công
                        .permitAll() // Cho phép mọi người truy cập trang login
                        .failureHandler(customAuthenticationFailureHandler) // Đăng ký failure handler

                );

        return http.build();
    }
}
