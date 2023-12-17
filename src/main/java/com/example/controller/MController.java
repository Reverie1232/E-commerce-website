package com.example.controller;
import com.example.pojo.Merchant;
import com.example.service.MerchantService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("merchant")
public class MController {
    @Resource
    private MerchantService merchantService;
    @PostMapping("login")
    public Map<String, Object> loginAccount(Merchant merchant, HttpSession session){
        return merchantService.loginAccount(merchant, session);
    }
}
