package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin_login {
    private int id;
    private String name;
    private String ip;
    private LocalDateTime login_time;
}
