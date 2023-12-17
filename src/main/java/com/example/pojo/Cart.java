package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private int id;
    private int user_id;
    private int goods_id;
    private Goods goods;
    private int quantity;
}
