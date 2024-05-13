package com.example.service;

import com.example.mapper.DurationMapper;
import com.example.pojo.Duration;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DurationService {
    @Resource
    DurationMapper durationMapper;

    @Autowired
    public DurationService(DurationMapper durationMapper) {
        this.durationMapper = durationMapper;
    }

    public void recordStayDuration(int goodsId, int stayDuration, Integer user_id) {
        Duration duration = new Duration();
        duration.setGoods_id(goodsId);
        duration.setDuration(stayDuration);
        duration.setUser_id(user_id);
        durationMapper.recordStayDuration(duration);
    }
}
