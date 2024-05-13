package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Logout_logs {
    private int id;
    private int user_id;
    private String ip;
    private LocalDateTime logout_time;
}
