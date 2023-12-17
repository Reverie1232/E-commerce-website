package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private int order_id;
    private int user_id;
    private int shipping_address_id;
    private LocalDateTime order_date;
    private double totalPrice;
}
