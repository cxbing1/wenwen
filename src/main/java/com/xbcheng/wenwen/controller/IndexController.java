package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.mapper.QuestionMapper;
import com.xbcheng.wenwen.mapper.UserMapper;
import com.xbcheng.wenwen.model.Question;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.model.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @RequestMapping(value={"/index","/"})
    public String index(Model model){
        List<Integer> list = new ArrayList<>();

        for(int i=0;i<10;i++){
            list.add(i);
        }

        Map<String,Object> vo = new HashMap<>();
        Question question = questionMapper.selectByPrimaryKey(1);
        vo.put("question",question);
        vo.put("user",userMapper.selectByPrimaryKey(question.getUserId()));
        vo.put("followCount",100);
        model.addAttribute("vo",vo);
        model.addAttribute("list",list);

        User user = userMapper.selectByPrimaryKey(1);

        return "index";
    }

    @RequestMapping("/home")
    public String home(){
        return "profile";
    }
}
