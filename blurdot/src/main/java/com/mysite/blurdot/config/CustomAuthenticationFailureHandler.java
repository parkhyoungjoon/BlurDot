package com.mysite.blurdot.config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, 
                                        HttpServletResponse response, 
                                        AuthenticationException exception) throws IOException {
    	System.out.println("Authentication failed: " + exception.getMessage());
        // 로그인 실패 원인에 따른 에러 메시지 설정
        String errorMessage = "아이디 또는 비밀번호가 잘못되었습니다.";
        Throwable cause = exception.getCause();
        if (cause instanceof EmailNotVerifiedException) {
            errorMessage = cause.getMessage();
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 잘못되었습니다.";
        }
        
        // 세션에 에러 메시지 저장
        request.getSession().setAttribute("errorMessage", errorMessage);
        
        // 로그인 페이지로 리다이렉트
        response.sendRedirect("/user/login_form");
    }
}