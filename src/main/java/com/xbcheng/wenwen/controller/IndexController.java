package com.xbcheng.wenwen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(Model model){
        List<Integer> list = new ArrayList<>();

        for(int i=0;i<10;i++){
            list.add(i);
        }
        model.addAttribute("list",list);

        return "index";
    }

    @RequestMapping("/home")
    public String home(){
        return "profile";
    }
}
