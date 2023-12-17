package com.example.service;

import com.example.mapper.AddressMapper;
import com.example.pojo.Address;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressService {
    @Resource
    private AddressMapper addressMapper;
    public Address getAddressById(int address_id){
        return addressMapper.getAddressById(address_id);
    }
    public List<Address> getAddressByUserId(int user_id){
        return addressMapper.getAddressByUser(user_id);
    }

    public void deleteGoods(int id) {
        addressMapper.deleteById(id);
    }

    public Map<String, Object> updateAddress(int addressId, String streetAddress, String area, String city, String province, String recipientName, String phoneNumber) {
        Address address = addressMapper.getAddressById(addressId);
        address.setStreet_address(streetAddress);
        address.setArea(area);
        address.setCity(city);
        address.setProvince(province);
        address.setRecipient_name(recipientName);
        address.setPhone_number(phoneNumber);
        addressMapper.updateAddress(address);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 200);
        resultMap.put("message", "Edit Successfully");
        return resultMap;
    }

    public Map<String, Object> addAddress(int userId, String streetAddress, String area, String city, String province, String recipientName, String phoneNumber) {
        Address address = new Address();
        address.setUser_id(userId);
        address.setStreet_address(streetAddress);
        address.setArea(area);
        address.setCity(city);
        address.setProvince(province);
        address.setRecipient_name(recipientName);
        address.setPhone_number(phoneNumber);
        address.setIs_valid(1);
        addressMapper.addAddress(address);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 200);
        resultMap.put("message", "Edit Successfully");
        return resultMap;
    }
}
