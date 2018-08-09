package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.mapper.UserMapper;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.repository.UserRepository;
import com.xbcheng.wenwen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("register")
    public String register(String userName, String password, Model model, HttpServletRequest request){

            String msg = userService.registerService(userName, password);

            if(msg!=null){
                model.addAttribute("msg",msg);
                return "login";
            }

            request.getSession().setAttribute("user",userService.findByName(userName));
            return "index";

    }



}
