package com.example.controller;

import com.example.mapper.UserMapper;
import com.example.pojo.*;
import com.example.service.AddressService;
import com.example.service.GoodsService;
import com.example.service.OrderService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("orders")
    public String showUserOrders(Model model, HttpSession session){
        //获取用户id
        Integer user_id = (Integer) session.getAttribute("user_id");
        if(user_id == null){
            return "redirect:/login";
        }
        //根据用户id获取订单列表
        List<Order> userOrders = orderService.getOrdersByUserId(user_id);
        User user = userMapper.selectUserByUserId(user_id);
        model.addAttribute("user", user);
        model.addAttribute("userOrders", userOrders);
        return "orders";
    }
    @GetMapping("user-orders")
    public String showAllUserOrders(Model model, HttpSession session){
        //获取商家用户名检验是否登录
        String name = (String) session.getAttribute("merchant_name");
        if(name == null){
            return "redirect:/merchantLogin";
        }
        //获取订单列表
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("orderList", orderList);
        return "all-orders";
    }
    @GetMapping("/order/details/{orderId}")
    public String showOrderDetails(Model model, @PathVariable Integer orderId, HttpSession session) {
        //获取用户id
        Integer user_id = (Integer) session.getAttribute("user_id");
        if(user_id == null){
            return "redirect:/login";
        }
        User user = userMapper.selectUserByUserId(user_id);
        model.addAttribute("user", user);
        // 根据订单 ID 获取订单详情
        int order_id = orderId;
        Order order = orderService.getOrdersByOrderId(order_id);
        int address_id = order.getShipping_address_id();
        Address address = addressService.getAddressById(address_id);
        List<OrderItem> orderItems = orderService.getItemsByOrderId(order_id);
        List<Goods> Items = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            Goods goods = goodsService.getGoodsById(orderItem.getGoods_id());
            Items.add(goods);
            quantities.add(orderItem.getQuantity());
        }
        model.addAttribute("order", order);
        model.addAttribute("address", address);
        model.addAttribute("Items", Items);
        model.addAttribute("quantities", quantities);
        return "orderDetails";
    }

    @GetMapping("/all-order/details/{orderId}")
    public String showAllOrderDetails(Model model, @PathVariable Integer orderId, HttpSession session) {
        //获取商家用户名检验是否登录
        String name = (String) session.getAttribute("merchant_name");
        if(name == null){
            return "redirect:/merchantLogin";
        }
        // 根据订单 ID 获取订单详情
        int order_id = orderId;
        Order order = orderService.getOrdersByOrderId(order_id);
        User user = userMapper.selectUserByUserId(order.getUser_id());
        int address_id = order.getShipping_address_id();
        Address address = addressService.getAddressById(address_id);
        List<OrderItem> orderItems = orderService.getItemsByOrderId(order_id);
        List<Goods> Items = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            Goods goods = goodsService.getGoodsById(orderItem.getGoods_id());
            Items.add(goods);
            quantities.add(orderItem.getQuantity());
        }
        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("address", address);
        model.addAttribute("Items", Items);
        model.addAttribute("quantities", quantities);
        return "allOrderDetails";
    }
}
