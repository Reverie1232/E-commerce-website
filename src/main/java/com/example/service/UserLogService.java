package com.example.service;

import com.example.mapper.UserLogMapper;
import com.example.pojo.UserLog;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserLogService {
    @Resource
    private UserLogMapper userLogMapper;
    @Autowired
    public UserLogService(UserLogMapper userLogMapper){
         this.userLogMapper = userLogMapper;
     }

    public void logUserAction(String username, String goods_name, String action, int seller){
        UserLog userLog = new UserLog();
        userLog.setUsername(username);
        userLog.setTimestamp(LocalDateTime.now());
        userLog.setGoods_name(goods_name);
        userLog.setAction(action);
        userLog.setSeller(seller);
        userLogMapper.insertUserLog(userLog);
    }

    public List<UserLog> getUserLog(int seller){
        return userLogMapper.getUserLog(seller);
    }

    public void clearLogs(int seller) {
        userLogMapper.clearLogs(seller);
    }
}
