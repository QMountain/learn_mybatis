package com.qm.learnmybatis.controller;

import com.qm.learnmybatis.dto.LoginRequest;
import com.qm.learnmybatis.dto.LoginResponse;
import com.qm.learnmybatis.service.IUserService;
import com.qm.learnmybatis.util.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private IUserService userService;
    
    @Autowired
    private SessionManager sessionManager;

    @PostMapping("/auth")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        // 这里简化处理，实际项目中应该查询数据库验证用户名密码
        // 假设用户名为admin，密码为123456
        if ("admin".equals(loginRequest.getUsername()) && "123456".equals(loginRequest.getPassword())) {
            // 创建会话
            String sessionId = sessionManager.createSession(1L, loginRequest.getUsername());
            return new LoginResponse(true, "登录成功", sessionId, 1L, loginRequest.getUsername());
        } else {
            return new LoginResponse(false, "用户名或密码错误");
        }
    }
    
    @PostMapping("/logout")
    public LoginResponse logout(HttpServletRequest request) {
        String sessionId = request.getHeader("sessionId");
        if (sessionId == null) {
            sessionId = request.getParameter("sessionId");
        }
        
        if (sessionId != null) {
            sessionManager.removeSession(sessionId);
        }
        
        return new LoginResponse(true, "登出成功");
    }
    
    @GetMapping("/test")
    public String test() {
        return "登录测试接口";
    }
}