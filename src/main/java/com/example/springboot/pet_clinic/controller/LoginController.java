package com.example.springboot.pet_clinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/")
    public String redirect(){
        return "redirect:/show-login-page";
    }
    @GetMapping("/show-login-page")
    public String showLoginPage(){
        return "fancy-login";
    }
    // add request mapping for /access-denied
    @GetMapping("/access-denied")
    public String showAccessDenied(){
        return "access-denied";
    }
}