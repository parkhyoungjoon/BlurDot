package com.mysite.blurdot;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

	@GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("title", "BlurDot");
        model.addAttribute("metaDescription", "Welcome to BlurDot!");
        return "index";
    }
}