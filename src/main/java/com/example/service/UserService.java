package com.example.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.mapper.CartMapper;
import com.example.mapper.UserMapper;
import com.example.pojo.Cart;
import com.example.pojo.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private MailService mailService;
    /**
     * 注册账号
     *
     */
    @Transactional
    public Map<String, Object> createAccount(User user){
        // 检测两次密码是否匹配
        if (!user.getPassword().equals(user.getConfirm_password())){
            // 两次密码不匹配，返回失败信息
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("code", 400);
            resultMap.put("message", "两次密码不一致，请重新输入");
            return resultMap;
        }
        List<User> userList = userMapper.selectUserByEmail(user.getEmail());
        // 查询不到结果，返回；该账户不存在或未激活
        // 检测邮箱是否已注册
        if(!userList.isEmpty()){
            // 邮箱已注册，返回失败信息
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("code", 400);
            resultMap.put("message", "注册失败，该邮箱已被注册");
            return resultMap;
        }
        // 雪花算法生成确认码
        String confirmCode = IdUtil.getSnowflake(1,1).nextIdStr();
        // 获取用户名
        String username = user.getUsername();
        // 生成盐
        String salt = RandomUtil.randomString(6);
        // 加密密码：原始密码+盐
        String md5Pwd = SecureUtil.md5(user.getPassword()+salt);
        // 激活失效时间：24小时
        LocalDateTime ldt = LocalDateTime.now().plusDays(1);
        // 初始化账号信息
        user.setUsername(username);
        user.setSalt(salt);
        user.setPassword(md5Pwd);
        user.setConfirm_code(confirmCode);
        user.setActivation_time(ldt);
        user.setIs_valid((byte)0);
        // 新增账号
        int result = userMapper.insertUser(user);
        Map<String, Object> resultMap = new HashMap<>();
        if (result > 0){
            // 发送邮件
            String activationUrl = "http://8.134.166.18:8080/user/activation?confirmCode=" + confirmCode;
            mailService.sendMailForActivateAccount(activationUrl, user.getEmail());
            resultMap.put("code", 200);
            resultMap.put("message", "注册成功,请前往邮箱进行账号激活");
        } else {
            resultMap.put("code", 400);
            resultMap.put("message", "注册失败");
        }
        return resultMap;
    }
    /**
     * 登陆账号
     *
     */
    public Map<String, Object> loginAccount(User user, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        // 根据邮箱查询用户
        List<User> userList = userMapper.selectUserByEmail(user.getEmail());
        // 查询不到结果，返回；该账户不存在或未激活
        if(userList == null || userList.isEmpty()){
            resultMap.put("code", 400);
            resultMap.put("message", "该账户不存在或未激活");
            return resultMap;
        }
        // 查询到多个账户，返回；账号异常
        if(userList.size()>1){
            resultMap.put("code", 400);
            resultMap.put("message", "账号异常");
            return resultMap;
        }
        // 查询到一个用户，进行密码比对
        User u = userList.get(0);
        // 用户输入的密码和salt进行加密
        String md5pwd = SecureUtil.md5(user.getPassword() + u.getSalt());
        // 密码不一致，返回；邮箱或密码错误
        if(!u.getPassword().equals(md5pwd)){
            resultMap.put("code", 400);
            resultMap.put("message", "邮箱或密码错误");
            return resultMap;
        }
        resultMap.put("code", 200);
        resultMap.put("message", "登陆成功");
        //session记录用户的id
        session.setAttribute("user_id", u.getId());
        return resultMap;
    }

    /**
     * 激活账号
     *
     */
    public Map<String, Object> activationAccount(String confirmCode) {
        Map<String, Object> resultMap = new HashMap<>();
        // 根据确认码查询用户
        User user = userMapper.selectUserByConfirmCode(confirmCode);
        if(user == null){
            resultMap.put("code", 400);
            resultMap.put("message", "该账号已激活");
            return resultMap;
        }
        boolean after = LocalDateTime.now().isAfter(user.getActivation_time());
        if(after){
            resultMap.put("code", 400);
            resultMap.put("message", "链接已失效，请重新注册");
            return resultMap;
        }
        // 根据确认码查询用户并修改状态值为1（可用）
        int result = userMapper.updateUserByConfirmCode(confirmCode);
        if(result > 0){
            resultMap.put("code", 200);
            resultMap.put("message", "激活成功");
        }else {
            resultMap.put("code", 400);
            resultMap.put("message", "激活失败");
        }
        return resultMap;
    }

    public User getUserById(int userId) {
        return userMapper.selectUserByUserId(userId);
    }
}
