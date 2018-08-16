package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.mapper.UserMapper;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.repository.UserRepository;
import com.xbcheng.wenwen.service.UserService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/reg")
    public String register(String username, String password,String next, Model model, HttpSession session){

        Map<String,Object> map= userService.registerService(username, password);

        if(map.containsKey("msg")){
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }

        session.setAttribute("user",map.get("user"));

        if(!StringUtil.isNullOrEmpty(next)){
            return "redirect:"+next;
        }
        return "redirect:/";

    }

    @PostMapping("/login")
    public String login(String username, String password,String next,Model model,HttpSession session){

        Map<String,Object> map = userService.loginService(username,password);

        if(map.containsKey("msg")){
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }

        session.setAttribute("user",map.get("user"));

        if(!StringUtil.isNullOrEmpty(next)){
            return "redirect:"+next;
        }
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:index";
    }

    @RequestMapping("/reglogin")
    public String reglogin(String next,Model model){
        model.addAttribute("next",next);
        return "login";
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable Integer id,Model model){

        User user = userService.findById(id);

        model.addAttribute("user",user);

        return "home";


    }




}
