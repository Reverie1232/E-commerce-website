package com.example.mapper;

import com.example.pojo.UserLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserLogMapper {
    @Insert("INSERT INTO userlog (username, timestamp, goods_name, action, seller)" +
            "VALUES (#{username}, #{timestamp}, #{goods_name}, #{action}, #{seller})")
    void insertUserLog(UserLog userLog);

    @Select("SELECT * FROM userlog WHERE seller = #{seller}")
    @Results({
            @Result(property = "goods_name", column = "goods_name"),
    })
    List<UserLog> getUserLog(int seller);

    @Delete("DELETE FROM userlog WHERE seller = #{seller}")
    void clearLogs(int seller);
}
