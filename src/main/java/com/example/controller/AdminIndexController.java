package com.example.controller;

import com.example.pojo.*;
import com.example.service.AdminService;
import com.example.service.GoodsService;
import com.example.service.MerchantService;
import com.example.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("admin")
public class AdminIndexController {
    @Resource
    private AdminService adminService;
    private final MerchantService merchantService;
    private final GoodsService goodsService;
    private final OrderService orderService;

    public AdminIndexController(MerchantService merchantService, GoodsService goodsService, OrderService orderService) {
        this.merchantService = merchantService;
        this.goodsService = goodsService;
        this.orderService = orderService;
    }

    // 登陆账号
    @PostMapping("login")
    public Map<String, Object> loginAdmin(Admin admin, HttpSession session, HttpServletRequest request) {
        return adminService.loginAdmin(admin, session, request);
    }

    //获取销售人员信息
    @GetMapping("salespersons")
    public List<Merchant> getAllMerchants() {
        return merchantService.getAllMerchants();
    }

    //获取销售人员信息
    @GetMapping("goods")
    public List<Goods> getAllGoods() {
        return goodsService.getAllGoods();
    }

    @GetMapping("logout")
    public ResponseEntity<String> logout(HttpSession session, HttpServletRequest request) {
        // 在后端进行用户注销的逻辑处理，比如清除会话信息等

        // 例如，清除当前用户的会话信息
        session.removeAttribute("admin");
        adminService.logout(request);
        // 返回一个成功注销的响应
        return ResponseEntity.ok("Logout successful.");
    }

    //重置密码
    @PostMapping("{id}/reset")
    public String resetPassword(@PathVariable int id) {
        merchantService.resetById(id);
        return "Password(default:123123) reset for salesperson with ID: " + id;
    }

    //删除销售人员
    @DeleteMapping("delete/{id}")
    public String deleteSalesperson(@PathVariable int id) {
        merchantService.deleteById(id);
        return "Salesperson with ID: " + id + " deleted";
    }

    @PostMapping("addSalesperson")
    public ResponseEntity<String> addSalespersons(@RequestParam String name, @RequestParam String password) {
        try {
            merchantService.addMerchant(name, password);
            return ResponseEntity.ok("Salesperson added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add salesperson.");
        }
    }

    @GetMapping("goods/sales")
    public List<MonthSales> getMonthSalesById(@RequestParam("goodsId") Integer goodsId) {
        return goodsService.getMonthSalesById(goodsId);
    }

    @GetMapping ("map")
    public List<ProvinceSales> getProvinceSales() {
        return orderService.getProvinceSales();
    }
}
