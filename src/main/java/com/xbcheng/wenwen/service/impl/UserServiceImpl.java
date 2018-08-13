package com.xbcheng.wenwen.service.impl;

import com.xbcheng.wenwen.mapper.UserMapper;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.UserService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public String registerService(String userName, String password) {

        if(StringUtil.isNullOrEmpty(userName)){
            return "用户名不能为空";
        }

        if(StringUtil.isNullOrEmpty(password)){
            return "密码不能为空";
        }

        if(userMapper.selectByName(userName)!=null){
            return "用户名已存在";
        }

        User user = new User();
        user.setName(userName);
        user.setSalt(UUID.randomUUID().toString());
        user.setPassword(DigestUtils.md5DigestAsHex((password+user.getSalt()).getBytes()));

        userMapper.insert(user);

        return null;


    }

    @Override
    public User findByName(String name) {
        return userMapper.selectByName(name);
    }
}
