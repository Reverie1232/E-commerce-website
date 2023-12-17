package com.example.controller;

import com.example.mapper.UserMapper;
import com.example.pojo.Goods;
import com.example.pojo.User;
import com.example.service.GoodsService;
import com.example.service.UserLogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@Controller
public class GoodsController {
    @Resource
    private final GoodsService goodsService;
    @Resource
    private final UserLogService userLogService;
    @Resource
    public UserMapper userMapper;

    @Autowired
    public GoodsController(GoodsService goodsService, UserMapper userMapper, UserLogService userLogService) {
        this.goodsService = goodsService;
        this.userMapper = userMapper;
        this.userLogService = userLogService;
    }

    @GetMapping("goods")
    public String showGoodsList(@org.jetbrains.annotations.NotNull Model model, HttpSession session){
        // 获取已登录用户的信息
        Integer user_id = (Integer) session.getAttribute("user_id");
        //如果用户ID不为空，从数据库查询用户信息
        if (user_id != null) {
            User loggedInUser = userMapper.selectUserByUserId(user_id);
            model.addAttribute("loggedInUser", loggedInUser);
        }
        List<Goods> goodsList = goodsService.getAllGoods();
        model.addAttribute("goodsList", goodsList);
        return "goodsList";
    }

    @GetMapping("goods/{id}")
    public String showGoodsDetail(@PathVariable Integer id, @NotNull Model model, HttpSession session){
        // 获取已登录用户的信息
        Integer user_id = (Integer) session.getAttribute("user_id");
        Goods goods = goodsService.getGoodsById(id);
        model.addAttribute("goods", goods);
        //如果用户ID不为空，从数据库查询用户信息并记录用户浏览行为
        if (user_id != null) {
            User loggedInUser = userMapper.selectUserByUserId(user_id);
            model.addAttribute("loggedInUser", loggedInUser);
            String username = loggedInUser.getUsername();
            userLogService.logUserAction(username, goods.getName(), "VIEW");
        }
        return "goodsDetail";
    }
}
