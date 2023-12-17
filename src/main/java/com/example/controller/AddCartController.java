package com.example.controller;

import com.example.service.CartService;
import com.example.service.GoodsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AddCartController {
    @Resource
    private final CartService cartService;
    @Autowired
    public AddCartController(CartService cartService){
        this.cartService = cartService;
    }
    /**
     * 添加购物车
     */
    @PostMapping("add")
    public Map<String, Object> addToCart(HttpSession session, @RequestParam("goods_id") int goods_id, @RequestParam("quantity") int quantity){
        Integer user_id = (Integer) session.getAttribute("user_id");
        Map<String, Object> result = new HashMap<>();
        if (user_id == null) {
            result.put("code", 400);
            result.put("message", "User not logged in");
        } else {
            // 调用购物车服务将商品添加到购物车
            cartService.addToCart(user_id, goods_id, quantity);
            result.put("code", 200);
            result.put("message", "Added to cart successfully");
        }
        return result;
    }
}
