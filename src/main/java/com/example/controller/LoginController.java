package com.example.controller;

import com.example.service.LogsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class LoginController {
    @Resource
    private LogsService logsService;

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("logout")
    public String logout(HttpSession session, HttpServletRequest request){
        logsService.logout_log((int)session.getAttribute("user_id"), request.getRemoteAddr(), LocalDateTime.now());
        //使Session失效
        session.removeAttribute("user_id");
        return "redirect:/login";
    }
}
