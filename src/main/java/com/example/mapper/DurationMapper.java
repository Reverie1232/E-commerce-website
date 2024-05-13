package com.example.mapper;

import com.example.pojo.Duration;
import org.apache.ibatis.annotations.Insert;

public interface DurationMapper {
    @Insert("INSERT INTO duration (user_id, duration, goods_id) VALUES (#{user_id}, #{duration}, #{goods_id})")
    void recordStayDuration(Duration duration);
}
