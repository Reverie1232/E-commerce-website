package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MerchantLoginController {
    @GetMapping("merchantLogin")
    public String login(){
        return "merchantLogin";
    }
}
