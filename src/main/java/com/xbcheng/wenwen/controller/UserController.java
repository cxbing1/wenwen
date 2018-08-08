package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.mapper.UserMapper;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user")
    @ResponseBody
    public User userList(){

        return userMapper.selectByPrimaryKey(1);
    }

    @RequestMapping("/saveUser")
    @ResponseBody
    public User saveUser(){
        User user = userMapper.selectByPrimaryKey(1);
        userRepository.save(user);
        return userRepository.findAll().iterator().next();
    }

}
