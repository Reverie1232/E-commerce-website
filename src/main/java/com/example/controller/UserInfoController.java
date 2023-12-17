package com.example.controller;

import com.example.pojo.Address;
import com.example.pojo.User;
import com.example.service.AddressService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class UserInfoController {
    @Autowired
    AddressService addressService;
    @Autowired
    UserService userService;
    //展示个人信息
    @GetMapping("info")
    public String showInfo(Model model, HttpSession session){
        Integer user_id = (Integer) session.getAttribute("user_id");
        if(user_id == null){
            return "redirect:/login";
        }
        User user = userService.getUserById(user_id);
        List<Address> addresses = addressService.getAddressByUserId(user_id);
        model.addAttribute("user", user);
        model.addAttribute("addresses", addresses);
        return "information";
    }

    @PostMapping("/remove-address")
    public String removeProduct(@RequestParam("addressId") int id){
        addressService.deleteGoods(id);
        return "redirect:/info";
    }

    @GetMapping("/edit-address/{addressId}")
    public String editAddress(@PathVariable int addressId, Model model) {
        Address address = addressService.getAddressById(addressId);
        if(address == null){
            return "redirect:/info";
        }
        model.addAttribute("address", address);
        return "edit-address";
    }

    @GetMapping("/add-address")
    public String editAddress(Model model, HttpSession session) {
        int userId = (int) session.getAttribute("user_id");
        model.addAttribute("userId", userId);
        return "add-address";
    }
}
