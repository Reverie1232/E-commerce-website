package com.example.mapper;

import com.example.pojo.UserLog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.util.List;

public interface UserLogMapper {
    @Insert("INSERT INTO userlog (username, timestamp, goods_name, action)" +
            "VALUES (#{username}, #{timestamp}, #{goods_name}, #{action})")
    void insertUserLog(UserLog userLog);

    @Select("SELECT * FROM userlog")
    @Results({
            @Result(property = "goods_name", column = "goods_name"),
    })
    List<UserLog> getUserLog();

    @Delete("DELETE FROM userlog")
    void clearLogs();
}
