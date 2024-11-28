package com.mysite.blurdot.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.blurdot.email.EmailService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CategoryService categoryService;  // CategoryService injection
    private final EmailService emailService;
    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
    
    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByUserEmail(email);
    }
    
    public boolean isNameDuplicate(String name) {
        return userRepository.existsByUserName(name);
    }
    
    // 회원가입
    public String registerUser(UserDTO userDTO) {
        // Password hashing
        String hashedPassword = passwordEncoder.encode(userDTO.getUserPassword1());
        // Category lookup
        Category category = categoryService.findByCategoryId(userDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate userBirthday = LocalDate.parse(userDTO.getUserAge(), formatter);
        
        String phoneNumber = userDTO.getUserPhonenumber();
        phoneNumber = phoneNumber.replace("-","");
        
        // Create and save User entity
        SiteUser user = new SiteUser();
        user.setUserEmail(userDTO.getUserEmail());
        user.setUserPassword(hashedPassword);
        user.setUserName(userDTO.getUserName());
        user.setUserPhonenumber(phoneNumber);
        user.setUserAge(userBirthday);
        user.setUserGender(userDTO.getUserGender());
        user.setCategory(category);
        user.setUserDate(LocalDateTime.now());
        user.setUser_storage_size(0);
        user.setRole(UserRole.USER);
        user.setIsVerified(false);
        // Save the user entity to the repository
        userRepository.save(user);
        
        emailService.sendMail(userDTO.getUserEmail(),user);

        return "회원가입 성공하셨습니다. 로그인 부탁드려요!";
    }
    
    // ADMIN이 다른 사용자의 권한을 변경하는 메서드
    @Transactional
    public void changeUserRole(String userEmail, UserRole newRole) {
        SiteUser user = userRepository.findByUserEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        user.setRole(newRole);
        userRepository.save(user);
    }
}	

