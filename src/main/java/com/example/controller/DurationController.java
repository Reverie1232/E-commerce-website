package com.example.controller;

import com.example.service.DurationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.attoparser.trace.MarkupTraceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class DurationController {
    @Resource
    private DurationService durationService;

    @Autowired
    public DurationController(DurationService durationService) {
        this.durationService = durationService;
    }

    @PostMapping("record_stay_duration")
    public ResponseEntity<String> recordStayDuration(HttpSession session, @RequestParam("goods_id") int goods_id, @RequestParam("stayDuration") int stayDuration) {
        Integer user_id = (Integer) session.getAttribute("user_id");
        if(user_id == null){
            return ResponseEntity.ok("");
        }
        durationService.recordStayDuration(goods_id, stayDuration, user_id);
        return ResponseEntity.ok("");
    }
}
