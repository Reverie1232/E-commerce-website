package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller_login {
    private int id;
    private int seller;
    private String ip;
    private LocalDateTime login_time;
}
