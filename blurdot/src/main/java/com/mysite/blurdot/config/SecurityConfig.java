package com.mysite.blurdot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 패스워드 암호화 관련 메소드
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/user/login", "/user/logout", "/user/mail_chk") // CSRF 예외 처리
        )
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/", "/user/login_form", "/user/join_form", "/user/mail_chk" , "/js/**", "/css/**", "/images/**").permitAll()
            .requestMatchers("/user/**").authenticated()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .usernameParameter("userEmail")
            .passwordParameter("password")
            .loginPage("/user/login_form")
            .loginProcessingUrl("/user/login")
            .failureHandler(customAuthenticationFailureHandler)
            .defaultSuccessUrl("/")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
        )
        .sessionManagement(sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 인증 시에만 세션 생성
        );
        return http.build();
    }
}