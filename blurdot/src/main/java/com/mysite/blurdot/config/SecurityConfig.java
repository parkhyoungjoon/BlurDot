package com.mysite.blurdot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 패스워드 암호화 관련 메소드
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 및 기본 인증 비활성화
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            // 권한 설정
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/","/user/**", "/js/**", "/css/**","/images/**").permitAll() // 허용 경로
                .anyRequest().authenticated() // 나머지는 인증 필요
            )

            // 폼 로그인 설정 (필요 시 JWT를 사용하면 이 섹션 제거 가능)
            .formLogin(form -> form
                .loginPage("/user/login_form") // 사용자 정의 로그인 페이지
                .loginProcessingUrl("/user/login") // 로그인 처리 URL
                .defaultSuccessUrl("/") // 로그인 성공 시 리다이렉트 URL
                .permitAll()
            )

            // 로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃 URL
                .logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트 URL
                .invalidateHttpSession(true) // 세션 무효화
            )

            // 세션 관리 설정
            .sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // 세션 생성 정책 설정
            );

        return http.build();
    }
}
