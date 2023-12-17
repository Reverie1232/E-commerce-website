package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private int id; //主键
    private String username; //用户名
    private String email; //邮箱
    private String password; //md5
    private String salt; //盐
    private String confirm_code; //确认码
    private LocalDateTime activation_time; //激活失效时间
    private Byte is_valid; //是否可用
    private String confirm_password;
}
