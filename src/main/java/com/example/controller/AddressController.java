package com.example.controller;

import com.example.mapper.UserMapper;
import com.example.pojo.Address;
import com.example.pojo.User;
import com.example.service.AddressService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AddressController {
    @Autowired
    AddressService addressService;
    @Autowired
    UserMapper userMapper;
    @PostMapping("/cart/checkout")
    public String selectAddress(Model model, HttpSession session){
        //获取用户id
        Integer user_id = (Integer) session.getAttribute("user_id");
        if(user_id == null){
            return "redirect:/login";
        }
        User user = userMapper.selectUserByUserId(user_id);
        List<Address> addresses = addressService.getAddressByUserId(user_id);
        model.addAttribute("user", user);
        model.addAttribute("addresses", addresses);
        return "selectAddress";
    }

    @PostMapping("address")
    public String saveAddress(Model model, HttpSession session){
        //获取用户id
        Integer user_id = (Integer) session.getAttribute("user_id");
        if(user_id == null){
            return "redirect:/login";
        }
        User user = userMapper.selectUserByUserId(user_id);
        List<Address> addresses = addressService.getAddressByUserId(user_id);
        model.addAttribute("user", user);
        model.addAttribute("addresses", addresses);
        return "addressList";
    }
}
