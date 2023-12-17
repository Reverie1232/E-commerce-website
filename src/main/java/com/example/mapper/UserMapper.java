package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.util.List;

public interface UserMapper {
    @Insert("INSERT INTO user (email, username, password, salt, confirm_code, activation_time, is_valid )" +
            "VALUES (#{email}, #{username}, #{password}, #{salt}, #{confirm_code}, #{activation_time}, #{is_valid})")
    int insertUser(User user);
    //根据确认码查询用户
    @Select("SELECT email, activation_time FROM user WHERE confirm_code = #{confirmCode} AND is_valid = 0")
    @Results({
            @Result(property = "email", column = "email"),
            @Result(property = "activation_time", column = "activation_time", typeHandler = LocalDateTimeTypeHandler.class),
    })
    User selectUserByConfirmCode(@Param("confirmCode") String confirmCode);
    /*
    根据确认码查询用户并更改状态为1
     */
    @Update("UPDATE user SET is_valid = 1 WHERE confirm_code = #{confirmCode}")
    int updateUserByConfirmCode(@Param("confirmCode") String confirmCode);

    /**
     * 根据邮箱查询
     *
     */
    @Select("SELECT id, email, password, salt FROM user WHERE email = #{email} AND is_valid = 1")
    List<User> selectUserByEmail(@Param("email") String email);
    /**
     * 根据用户ID查询用户
     */
    @Select("SELECT username, email FROM user WHERE id = #{user_id}")
    User selectUserByUserId(@Param("user_id") int user_id);

}
