package com.example.service;

import com.example.mapper.MerchantMapper;
import com.example.pojo.Merchant;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MerchantService {
    @Resource
    MerchantMapper merchantMapper;
    LogsService logsService;
    @Autowired
    public MerchantService(MerchantMapper merchantMapper, LogsService logsService) {
        this.merchantMapper = merchantMapper;
        this.logsService = logsService;
    }
    public Map<String, Object> loginAccount(Merchant merchant, HttpSession session, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        // 根据用户名查询用户
        Merchant m = merchantMapper.selectMerchantByName(merchant.getName());
        // 查询不到结果，返回；用户名或密码错误
        if(m == null){
            resultMap.put("code", 400);
            resultMap.put("message", "用户名或密码错误");
            return resultMap;
        }
        // 查询到一个用户，进行密码比对
        String password = merchant.getPassword();
        // 密码不一致，返回；用户名或密码错误
        if(!m.getPassword().equals(password)){
            resultMap.put("code", 400);
            resultMap.put("message", "用户名或密码错误");
            return resultMap;
        }
        resultMap.put("code", 200);
        resultMap.put("message", "登陆成功");
        //session记录商家的用户名
        logsService.seller_login(m.getID(), request.getRemoteAddr(), LocalDateTime.now());
        session.setAttribute("merchant_name", m.getName());
        session.setAttribute("seller", m.getID());
        return resultMap;
    }

    public List<Merchant> getAllMerchants() {
        return merchantMapper.getAllMerchants();
    }

    public void resetById(int id) {
        merchantMapper.resetById(id);
    }

    public void deleteById(int id) {
        merchantMapper.deleteById(id);
    }

    public void addMerchant(String name, String password) {
        Merchant merchant = new Merchant();
        merchant.setName(name);
        merchant.setPassword(password);
        merchantMapper.addMerchant(merchant);
    }
}
