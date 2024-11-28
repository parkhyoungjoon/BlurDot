package com.mysite.blurdot.user;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mysite.blurdot.config.EmailNotVerifiedException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SiteUser user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        
        // 디버깅용 로그
        System.out.println("User loaded: " + user.getUserEmail() + ", Verified: " + user.getIsVerified());

        // 이메일 인증 여부 확인
        if (!user.getIsVerified()) {
            throw new EmailNotVerifiedException("이메일 인증이 완료되지 않았습니다.");
        }
        return new CustomUserDetails(user);
    }
}