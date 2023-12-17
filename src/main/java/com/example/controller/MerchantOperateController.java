package com.example.controller;

import com.example.mapper.AddressMapper;
import com.example.service.AddressService;
import com.example.service.GoodsService;
import com.example.service.UserLogService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MerchantOperateController {
    @Resource
    private final GoodsService goodsService;
    private final AddressService addressService;

    @Autowired
    public MerchantOperateController(GoodsService goodsService, AddressService addressService) {
        this.goodsService = goodsService;
        this.addressService = addressService;

    }
    @PostMapping("/update-product")
    public Map<String, Object> updateProduct(@RequestParam int productId,
                                             @RequestParam String productName,
                                             @RequestParam String productDescription,
                                             @RequestParam double productPrice,
                                             @RequestParam int productStock) {
        return goodsService.updateProduct(productId, productName, productDescription, productPrice, productStock);
    }

    @PostMapping("/update-address")
    public Map<String, Object> updateAddress(@RequestParam int addressId,
                                             @RequestParam String streetAddress,
                                             @RequestParam String area,
                                             @RequestParam String city,
                                             @RequestParam String province,
                                             @RequestParam String recipientName,
                                             @RequestParam String phoneNumber) {
        return addressService.updateAddress(addressId, streetAddress, area, city, province, recipientName, phoneNumber);
    }

    @PostMapping("/add-address-form")
    public Map<String, Object> addAddress(@RequestParam int userId,
                                             @RequestParam String streetAddress,
                                             @RequestParam String area,
                                             @RequestParam String city,
                                             @RequestParam String province,
                                             @RequestParam String recipientName,
                                             @RequestParam String phoneNumber) {
        return addressService.addAddress(userId, streetAddress, area, city, province, recipientName, phoneNumber);
    }

}
