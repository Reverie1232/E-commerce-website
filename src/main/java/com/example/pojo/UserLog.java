package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLog {
    private int id;
    private String username;
    private LocalDateTime timestamp;
    private String goods_name;
    private String action;
    private int seller;
}
