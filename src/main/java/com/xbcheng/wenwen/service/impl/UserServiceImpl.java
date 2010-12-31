package com.xbcheng.wenwen.service.impl;

import com.xbcheng.wenwen.mapper.UserMapper;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.UserService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Map<String,Object> registerService(String userName, String password) {

        Map<String,Object>  map = new HashMap<>();
        if(StringUtil.isNullOrEmpty(userName)){
            map.put("msg","用户名不能为空");
            return map;
        }

        if(StringUtil.isNullOrEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        if(userMapper.selectByName(userName)!=null){
            map.put("msg","用户名已存在");
            return map;
        }

        User user = new User();
        user.setName(userName);
        user.setSalt(UUID.randomUUID().toString().substring(0,10));
        user.setPassword(DigestUtils.md5DigestAsHex((password+user.getSalt()).getBytes()));
        user.setHeadUrl("aadd7b895_m.jpg");

        userMapper.insertSelective(user);
        map.put("user",userMapper.selectByName(user.getName()));
        return map;
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> loginService(String userName, String password) {
        Map<String,Object>  map = new HashMap<>();
        if(StringUtil.isNullOrEmpty(userName)){
            map.put("msg","用户名不能为空");
            return map;
        }

        if(StringUtil.isNullOrEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user = userMapper.selectByName(userName);

        if(user==null){
            map.put("msg","用户名不存在");
            return map;
        }
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex((password+user.getSalt()).getBytes()))){
            map.put("msg","用户名或密码错误");
            return map;
        }

        map.put("user",user);
        return map;
    }


}
