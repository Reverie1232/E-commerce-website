package com.example.controller;

import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("index")
    public String index(Model model, HttpSession session){
        // 获取已登录用户的信息
        Integer user_id = (Integer) session.getAttribute("user_id");
        //如果用户ID不为空，从数据库查询用户信息
        if (user_id != null) {
            User loggedInUser = userMapper.selectUserByUserId(user_id);
            model.addAttribute("loggedInUser", loggedInUser);
        }
        return "goodsList";
    }
}
