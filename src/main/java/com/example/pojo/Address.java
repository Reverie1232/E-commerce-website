package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private int address_id;
    private int user_id;
    private String recipient_name;
    private String street_address;
    private String area;
    private String city;
    private String province;
    private String phone_number;
    private int is_valid;
}
