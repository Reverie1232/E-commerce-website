package com.example.controller;
import com.example.pojo.UserLog;
import com.example.service.UserLogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class UserLogController {
    @Resource
    private UserLogService userLogService;
    @GetMapping("/user-logs")
    public String showUserLogs(Model model , HttpSession session){
        String name = (String) session.getAttribute("merchant_name");
        if(name == null){
            return "redirect:/merchantLogin";
        }
        List<UserLog> userLogs = userLogService.getUserLog();
        model.addAttribute("userLogs", userLogs);
        return "userLogs";
    }
}
