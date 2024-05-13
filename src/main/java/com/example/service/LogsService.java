package com.example.service;

import com.example.mapper.LogsMapper;
import com.example.pojo.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class LogsService {
    @Resource
    private final LogsMapper logsMapper;

    @Autowired
    public LogsService(LogsMapper logsMapper) {
        this.logsMapper = logsMapper;
    }

    //插入用户登录日志
    public void login_log(int user_id, String remoteAddr, LocalDateTime now) {
        Login_logs login_logs = new Login_logs();
        login_logs.setUser_id(user_id);
        login_logs.setIp(remoteAddr);
        login_logs.setLogin_time(now);
        logsMapper.login_log(login_logs);
    }

    //插入用户登出日志
    public void logout_log(int user_id, String remoteAddr, LocalDateTime now) {
        Logout_logs logout_logs = new Logout_logs();
        logout_logs.setUser_id(user_id);
        logout_logs.setIp(remoteAddr);
        logout_logs.setLogout_time(now);
        logsMapper.logout_log(logout_logs);
    }

    public void admin_log(String name, String remoteAddr, LocalDateTime now) {
        Admin_login adminLogin = new Admin_login();
        adminLogin.setName(name);
        adminLogin.setIp(remoteAddr);
        adminLogin.setLogin_time(now);
        logsMapper.admin_login(adminLogin);
    }

    public void seller_login(int id, String remoteAddr, LocalDateTime now) {
        Seller_login sellerLogin = new Seller_login();
        sellerLogin.setSeller(id);
        sellerLogin.setIp(remoteAddr);
        sellerLogin.setLogin_time(now);
        logsMapper.seller_login(sellerLogin);
    }

    public void seller_logout(int id, String remoteAddr, LocalDateTime now) {
        Seller_logout sellerLogout = new Seller_logout();
        sellerLogout.setSeller(id);
        sellerLogout.setIp(remoteAddr);
        sellerLogout.setLogout_time(now);
        logsMapper.seller_logout(sellerLogout);
    }

    public void adminLogout(String remoteAddr, LocalDateTime now) {
        Admin_logout adminLogout = new Admin_logout();
        adminLogout.setName("admin");
        adminLogout.setIp(remoteAddr);
        adminLogout.setLogout_time(now);
        logsMapper.admin_logout(adminLogout);
    }
}
