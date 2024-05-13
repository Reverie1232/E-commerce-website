package com.example.service;

import cn.hutool.crypto.SecureUtil;
import com.example.mapper.AdminMapper;
import com.example.pojo.Admin;
import com.example.pojo.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Resource
    AdminMapper adminMapper;


    @Resource
    private LogsService logsService;

    public Map<String, Object> loginAdmin(Admin admin, HttpSession session, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        // 根据用户名查询管理员
        List<Admin> adminList = adminMapper.selectAdminByName(admin.getName());
        // 查询不到结果，返回；该账户不存在或未激活
        if(adminList == null || adminList.isEmpty()){
            resultMap.put("code", 400);
            resultMap.put("message", "该用户名不存在");
            return resultMap;
        }
        // 查询到多个账户，返回；账号异常
        if(adminList.size()>1){
            resultMap.put("code", 400);
            resultMap.put("message", "账号异常");
            return resultMap;
        }
        // 查询到一个用户，进行密码比对
        Admin a = adminList.get(0);

        // 密码不一致，返回；邮箱或密码错误
        if(!a.getPassword().equals(admin.getPassword())){
            resultMap.put("code", 400);
            resultMap.put("message", "用户名或密码错误");
            return resultMap;
        }
        resultMap.put("code", 200);
        resultMap.put("message", "登陆成功");
        //session记录用户的id
        session.setAttribute("admin", a.getId());
        logsService.admin_log(a.getName(), request.getRemoteAddr(), LocalDateTime.now());
        return resultMap;
    }

    public void logout(HttpServletRequest request) {
        logsService.adminLogout(request.getRemoteAddr(), LocalDateTime.now());
    }
}
