package com.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        //使Session失效
        session.removeAttribute("user_id");
        return "redirect:/login";
    }
}
