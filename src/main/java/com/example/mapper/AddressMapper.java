package com.example.mapper;

import com.example.pojo.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AddressMapper {
    //通过id获取地址
    @Select("SELECT * FROM addresses WHERE address_id = #{address_id}")
    @Results({
            @Result(property = "address_id", column = "address_id"),
            @Result(property = "recipient_name", column = "recipient_name"),
            @Result(property = "street_address", column = "street_address"),
            @Result(property = "area", column = "area"),
            @Result(property = "city", column = "city"),
            @Result(property = "province", column = "province"),
            @Result(property = "phone_number", column = "phone_number"),
    })
    Address getAddressById(@Param("address_id") int address_id);

    //获取用户设置的地址信息
    @Select("SELECT * FROM addresses WHERE user_id = #{user_id} AND is_valid = 1")
    @Results({
            @Result(property = "address_id", column = "address_id"),
            @Result(property = "recipient_name", column = "recipient_name"),
            @Result(property = "street_address", column = "street_address"),
            @Result(property = "area", column = "area"),
            @Result(property = "city", column = "city"),
            @Result(property = "province", column = "province"),
            @Result(property = "phone_number", column = "phone_number"),
    })
    List<Address> getAddressByUser(@Param("user_id") int user_id);

    @Delete("UPDATE addresses SET is_valid = 0 WHERE address_id=#{id}")
    void deleteById(int id);

    @Update("UPDATE addresses SET recipient_name = #{recipient_name}, street_address = #{street_address}, " +
            "area = #{area}, city = #{city}, province = #{province}, phone_number = #{phone_number} " +
            "WHERE address_id = #{address_id}")
    void updateAddress(Address address);

    @Insert("INSERT INTO addresses (user_id, recipient_name, street_address, area, city, province, phone_number, is_valid) " +
            "VALUES (#{user_id}, #{recipient_name}, #{street_address}, #{area}, #{city}, #{province}, #{phone_number}, #{is_valid})")
    void addAddress(Address address);
}
