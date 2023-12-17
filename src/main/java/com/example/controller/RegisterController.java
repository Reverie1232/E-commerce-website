package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class RegisterController {
    @GetMapping("register")
    public String register(){
        return "register";
    }
}
