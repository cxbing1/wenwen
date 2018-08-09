package com.xbcheng.wenwen.service;

import com.xbcheng.wenwen.model.User;

import java.util.Map;

public interface UserService {

    public String registerService(String userName, String password);
    public User findByName(String name);

}
