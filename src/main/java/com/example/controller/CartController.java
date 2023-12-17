package com.example.controller;

import com.example.mapper.UserMapper;
import com.example.pojo.Cart;
import com.example.pojo.Goods;
import com.example.pojo.User;
import com.example.service.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Mapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {
    @Resource
    private final CartService cartService;
    private final GoodsService goodsService;
    private final OrderService orderService;
    private final UserLogService userLogService;
    private final UserMapper userMapper;
    private final MailService mailService;

    @Autowired
    public CartController(CartService cartService, GoodsService goodsService, OrderService orderService, UserLogService userLogService, UserMapper userMapper, MailService mailService){
        this.cartService = cartService;
        this.goodsService = goodsService;
        this.userMapper = userMapper;
        this.orderService = orderService;
        this.userLogService = userLogService;
        this.mailService = mailService;
    }
    //获取购物车界面
    @GetMapping("cart")
    public String showCarts(Model model, HttpSession session){
        Integer id = (Integer) session.getAttribute("user_id");
        //获取购物车列表（根据userid）
        if( id != null) {
            List<Cart> carts = cartService.getCartByUserId(id);
            User loggedInUser = userMapper.selectUserByUserId(id);
            model.addAttribute("loggedInUser", loggedInUser);
            //计算总价
            double totalPrice = 0;
            for (Cart cart : carts) {
                Goods goods = goodsService.getGoodsById(cart.getGoods_id());
                cart.setGoods(goods);
                totalPrice += goods.getPrice() * (double) cart.getQuantity();

            }
            //将相关数据传到前端html
            model.addAttribute("carts", carts);
            model.addAttribute("totalPrice", totalPrice);
            return "cart";
        }else{
            return "redirect:/login";
        }
    }
    /**
     * 删除购物车项
     * @param cartItemId
     * @return
     */
    @PostMapping("/cart/remove")
    public String removeCartItem(@RequestParam("cartItemId") int cartItemId){
        cartService.deleteCart(cartItemId);
        return "redirect:/cart";
    }

    @PostMapping("/checkout/confirm")
    public String checkout(Model model, HttpSession session, @RequestParam int shippingAddress){
        //获取用户id
        Integer user_id = (Integer) session.getAttribute("user_id");
        if(user_id == null){
            return "redirect:/login";
        }
        //如果用户ID不为空，从数据库查询用户信息
        User loggedInUser = userMapper.selectUserByUserId(user_id);
        model.addAttribute("loggedInUser", loggedInUser);
        List<Cart> cartList = cartService.getCartByUserId(user_id);
        List<Goods> insufficientStockGoods = cartService.checkout(cartList);
        if(insufficientStockGoods.isEmpty()){
            double totalPrice = 0;
            for (Cart cart : cartList) {
                Goods goods = goodsService.getGoodsById(cart.getGoods_id());
                cart.setGoods(goods);
                totalPrice += goods.getPrice() * (double) cart.getQuantity();
            }
            //添加订单信息
            int order_id = orderService.addOrder(user_id, shippingAddress, totalPrice);
            //添加订单商品信息
            for (Cart cart : cartList) {
                Goods goods = goodsService.getGoodsById(cart.getGoods_id());
                userLogService.logUserAction(loggedInUser.getUsername(), goods.getName(), "BUY");
                orderService.insertOrderItem(order_id, goods.getId(), cart.getQuantity());
            }
            //修改货物库存
            cartService.updateGoodsStock(cartList);
            //清空购物车
            cartService.clearCart(user_id);
            model.addAttribute("message", "Checkout successful!");
            // 发送邮件
            String activationUrl = "http://localhost:8080/user/checkout";
            mailService.sendMailForCheckout(loggedInUser.getEmail());
        }else{
            model.addAttribute("insufficientStockGoods", insufficientStockGoods);
            model.addAttribute("message", "Checkout failed due to insufficient stock.");
        }
        return "checkout";
    }


}
