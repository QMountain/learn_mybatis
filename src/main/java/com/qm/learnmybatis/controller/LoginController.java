package com.qm.learnmybatis.controller;

import com.qm.learnmybatis.entity.User;
import com.qm.learnmybatis.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private IUserService userService;

    @ResponseBody
    @RequestMapping("/test")
    public String login() throws Exception {
        User user = userService.selectOne(1L);
        return user.toString();
    }
}
