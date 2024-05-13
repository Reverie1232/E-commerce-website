package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login_logs {
    private int id;
    private int user_id;
    private String ip;
    private LocalDateTime login_time;
}
