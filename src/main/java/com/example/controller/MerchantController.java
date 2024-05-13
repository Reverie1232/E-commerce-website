package com.example.controller;

import com.example.pojo.Goods;
import com.example.service.GoodsService;
import com.example.service.UserLogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MerchantController {
    @Resource
    private final GoodsService goodsService;
    private final UserLogService userLogService;

    @Autowired
    public MerchantController(GoodsService goodsService, UserLogService userLogService) {
        this.goodsService = goodsService;
        this.userLogService = userLogService;
    }


    @GetMapping("merchant")
    public String showGoods(@org.jetbrains.annotations.NotNull Model model, HttpSession session){
        //获取商家用户名检验是否登录
        Integer id = (Integer) session.getAttribute("seller");
        String name = (String) session.getAttribute("merchant_name");
        if(id == null){
            return "redirect:/merchantLogin";
        }
        model.addAttribute("name", name);
        List<Goods> goodsList = goodsService.getGoodsBySeller((int) session.getAttribute("seller"));
        model.addAttribute("goodsList", goodsList);
        return "merchant";
    }

    @GetMapping("/edit-product/{productId}")
    public String editProduct(@PathVariable int productId, Model model) {
        Goods goods = goodsService.getGoodsById(productId);
        if(goods == null){
            return "redirect:/merchant";
        }
        model.addAttribute("goods", goods);
        return "edit-product";
    }



    @PostMapping("/add-product")
    public String addProduct(@RequestParam("productName") String productName,
                             @RequestParam("productDescription") String productDescription,
                             @RequestParam("productPrice") double productPrice,
                             @RequestParam("productStock") int productStock,
                             HttpSession session){
        Goods goods = new Goods();
        goods.setName(productName);
        goods.setDescription(productDescription);
        goods.setPrice(productPrice);
        goods.setStock(productStock);
        goods.setSeller((Integer) session.getAttribute("seller"));
        goodsService.addGoods(goods);
        return "redirect:/merchant";
    }

    @PostMapping("/remove-product")
    public String removeProduct(@RequestParam("productId") int id){
        goodsService.deleteGoods(id);
        return "redirect:/merchant";
    }

    @PostMapping("/clear-logs")
    public String clearLogs(HttpSession session){
        userLogService.clearLogs((int) session.getAttribute("seller"));
        return "userLogs";
    }
}
