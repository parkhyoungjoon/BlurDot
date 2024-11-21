package com.mysite.blurdot.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class UserController {
	@GetMapping("/user/login_form")
	public String user_login(Model model) {
		model.addAttribute("title", "BlurDot Login");
        model.addAttribute("metaDescription", "Login Page");
		return "user/user_login";
	}
	
	@GetMapping("/user/join_form")
	public String user_join(Model model) {
		model.addAttribute("title", "BlurDot Join");
        model.addAttribute("metaDescription", "Join Page");
		return "user/user_join";
	}
	
	@GetMapping("/user/membership_form")
	public String user_membership(Model model) {
		model.addAttribute("title", "BlurDot Membership");
        model.addAttribute("metaDescription", "Membership Page");
		return "user/user_membership";
	}
}
