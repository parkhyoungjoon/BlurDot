package com.mysite.blurdot.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysite.blurdot.email.EmailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor  // final 필드에 대한 생성자 주입
public class UserController {

    private final UserService userService;
    private final CategoryService categoryService; // CategoryService 주입
    private final EmailService emailService;

    @GetMapping("/user/login_form")
    public String userLogin(Model model) {
    	UserDTO userDTO = new UserDTO();
    	model.addAttribute("userDTO", userDTO);
        model.addAttribute("title", "BlurDot Login");
        model.addAttribute("metaDescription", "Login Page");
        return "user/user_login";
    }

    @GetMapping("/user/join_form")
    public String userJoin(Model model) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserGender(0); // 기본값 설정
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("title", "BlurDot Join");
        model.addAttribute("metaDescription", "Join Page");
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "user/user_join";
    }


    @GetMapping("/user/membership_form")
    public String userMembership(Model model) {
        model.addAttribute("title", "BlurDot Membership");
        model.addAttribute("metaDescription", "Membership Page");
        return "user/user_membership";
    }
    @PostMapping("/user/join_form")
    public String registerUser(
        @Valid UserDTO userDTO, 
        BindingResult bindingResult,
        Model model
    ) {
    	// 유효성 검증 실패 시 처리
    	model.addAttribute("title", "BlurDot Join");
        model.addAttribute("metaDescription", "Join Page");
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        
        if (bindingResult.hasErrors()) {
            
            return "user/user_join";
        }
        // 이메일 중복 확인 (서비스 호출)
        if (userService.isEmailDuplicate(userDTO.getUserEmail())) {
        	bindingResult.rejectValue("userEmail", "emailDuplicate", "이미 사용 중인 이메일입니다.");
        	return "user/user_join";
        }
        // 닉네임 중복 확인 (서비스 호출)
        if (userService.isNameDuplicate(userDTO.getUserName())) {
        	bindingResult.rejectValue("userName", "nameDuplicate", "이미 사용 중인 닉네임입니다.");
        	return "user/user_join";
        }
    	// 비밀번호 일치 여부 검증
    	if (!userDTO.getUserPassword1().equals(userDTO.getUserPassword2())) {
            bindingResult.rejectValue("userPassword2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "user/user_join";
    	}

        // 사용자 등록
        userService.registerUser(userDTO);
        
        return "redirect:/user/mail_chk?email=" + userDTO.getUserEmail();
    }
    @GetMapping("/user/mail_chk")
    public String mail_chk(@RequestParam("email") String email, Model model) {
        model.addAttribute("title", "BlurDot Mailchk");
        model.addAttribute("metaDescription", "Mailchk Page");
        model.addAttribute("email", email);
        return "/user/user_mail";
    }
    
    @PostMapping("/user/verify")
    public String verifyEmail(
            @RequestParam("email") String email, 
            @RequestParam("code") String code, 
            RedirectAttributes redirectAttributes) {
        try {
            emailService.verifyEmail(email, code);
        } catch (IllegalArgumentException e) {
        	redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/mail_chk";
        }
        return "redirect:/user/msg?msgT=가입 완료&msgC=회원가입에 성공하셨습니다. 축하드립니다.";
    }
    
    @GetMapping("/user/msg")
    public String msg_out(@RequestParam("msgT") String msgT, @RequestParam("msgC") String msgC, Model model) {
        model.addAttribute("title", "BlurDot Mailchk");
        model.addAttribute("metaDescription", "Mailchk Page");
        model.addAttribute("msgTitle", msgT);
        model.addAttribute("msgContent", msgC);
        return "/user/user_message";
    }
}
