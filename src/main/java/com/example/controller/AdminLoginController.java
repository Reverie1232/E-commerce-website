package com.example.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {
    @GetMapping("adminLogin")
    public String login(){
        return "adminLogin";
    }

    @GetMapping("adminDashboard")
    public String dashBoard(HttpSession session) {
        Integer id = (Integer) session.getAttribute("admin");
        if (id != null) {
            return "adminDashboard";
        } else {
            return "adminLogin";
        }

    }
}
