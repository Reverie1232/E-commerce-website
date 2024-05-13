package com.example.mapper;

import com.example.pojo.Merchant;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MerchantMapper {
    @Select("SELECT * FROM merchant WHERE name = #{name}")
    Merchant selectMerchantByName(@Param("name") String name);

    @Select("SELECT * FROM merchant")
    List<Merchant> getAllMerchants();

    @Update("UPDATE merchant SET password = 123123 WHERE ID = #{id}")
    void resetById(int id);

    @Delete("DELETE FROM merchant WHERE ID = #{id}")
    void deleteById(int id);

    @Insert("INSERT INTO merchant (name, password) VALUES (#{name}, #{password})")
    void addMerchant(Merchant merchant);
}
