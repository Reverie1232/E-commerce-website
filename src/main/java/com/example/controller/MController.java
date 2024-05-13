package com.example.controller;
import com.example.pojo.Merchant;
import com.example.service.LogsService;
import com.example.service.MerchantService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("merchant")
public class MController {
    @Resource
    private MerchantService merchantService;

    @Resource
    private LogsService logsService;

    @PostMapping("login")
    public Map<String, Object> loginAccount(Merchant merchant, HttpSession session, HttpServletRequest request){
        return merchantService.loginAccount(merchant, session, request);
    }

    @GetMapping("logout")
    public ResponseEntity<String> logout(HttpSession session, HttpServletRequest request) {
        // 在后端进行用户注销的逻辑处理，比如清除会话信息等
        int id = (int) session.getAttribute("seller");
        // 例如，清除当前用户的会话信息
        session.removeAttribute("seller");
        logsService.seller_logout(id, request.getRemoteAddr(), LocalDateTime.now());
        // 返回一个成功注销的响应
        return ResponseEntity.ok("Logout successful.");
    }
}
