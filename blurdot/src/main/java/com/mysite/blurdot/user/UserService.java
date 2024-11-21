package com.mysite.blurdot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
    
    private UserRepository userRepository;

 // 회원가입
    public String registerUser(UserDTO userDTO) {
        // 이메일 중복 체크
    	if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
            return "Email is already taken.";
        }

        // 비밀번호 해싱
        String hashedPassword = passwordEncoder.encode(userDTO.getUserPassword());

        // User 엔티티 생성 및 저장
        User user = new User();
        user.setUserEmail(userDTO.getUserEmail());
        user.setUserPassword(hashedPassword);
        user.setUserName(userDTO.getUserName());
        user.setUserPhonenumber(userDTO.getUserPhonenumber());
        user.setUserAge(userDTO.getUserAge());
        user.setUserGender(userDTO.getUserGender());

        userRepository.save(user);

        return "User registered successfully!";
    }
    
}