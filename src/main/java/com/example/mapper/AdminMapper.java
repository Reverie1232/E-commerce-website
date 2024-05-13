package com.example.mapper;

import com.example.pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {
    @Select("SELECT id, name, password FROM admin WHERE name = #{name}")
    List<Admin> selectAdminByName(@Param("name") String name);
}
