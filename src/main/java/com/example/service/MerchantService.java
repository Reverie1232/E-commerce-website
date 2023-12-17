package com.example.service;

import cn.hutool.crypto.SecureUtil;
import com.example.mapper.MerchantMapper;
import com.example.pojo.Merchant;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MerchantService {
    @Resource
    MerchantMapper merchantMapper;
    public Map<String, Object> loginAccount(Merchant merchant, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        // 根据邮箱查询用户
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
        session.setAttribute("merchant_name", m.getName());
        return resultMap;
    }
}
