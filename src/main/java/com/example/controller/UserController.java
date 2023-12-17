package com.example.controller;

import com.example.pojo.User;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;


@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     *
     */
    @PostMapping("create")
    public Map<String, Object> createAccount(User user){
        return userService.createAccount(user);
    }

    /**
     * 登陆账号
     *
     */
    @PostMapping ("login")
    public Map<String, Object> loginAccount(User user, HttpSession session){
        return userService.loginAccount(user, session);
    }

    /**
     * 激活账号
     *
     */
    @GetMapping("activation")
    public Map<String, Object> activationAccount(String confirmCode){
       return userService.activationAccount(confirmCode);
    }



}
