package com.example.mapper;

import com.example.pojo.Goods;
import com.example.pojo.Merchant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MerchantMapper {
    @Select("SELECT * FROM merchant WHERE name = #{name}")
    Merchant selectMerchantByName(@Param("name") String name);

}
