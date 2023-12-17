package com.example.service;

import com.example.mapper.UserLogMapper;
import com.example.mapper.UserMapper;
import com.example.pojo.UserLog;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserLogService {
    @Resource
    private UserLogMapper userLogMapper;
    @Autowired
    public UserLogService(UserLogMapper userLogMapper){
         this.userLogMapper = userLogMapper;
     }

    public void logUserAction(String username, String goods_name, String action){
        UserLog userLog = new UserLog();
        userLog.setUsername(username);
        userLog.setTimestamp(LocalDateTime.now());
        userLog.setGoods_name(goods_name);
        userLog.setAction(action);
        userLogMapper.insertUserLog(userLog);
    }

    public List<UserLog> getUserLog(){
        return userLogMapper.getUserLog();
    }

    public void clearLogs() {
        userLogMapper.clearLogs();
    }
}
