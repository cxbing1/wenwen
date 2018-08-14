package com.xbcheng.wenwen.service;

import com.xbcheng.wenwen.model.User;

import java.util.Map;

public interface UserService {

    public Map<String,Object> registerService(String userName, String password);
    public User findById(Integer id);

    public Map<String,Object> loginService(String userName, String password);


}
