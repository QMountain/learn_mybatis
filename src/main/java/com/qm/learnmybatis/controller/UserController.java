package com.qm.learnmybatis.controller;

import com.qm.learnmybatis.entity.User;
import com.qm.learnmybatis.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private IUserService userService;
    
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        try {
            return userService.selectOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}