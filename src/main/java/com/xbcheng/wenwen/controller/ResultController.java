package com.xbcheng.wenwen.controller;

import com.github.pagehelper.PageInfo;
import com.xbcheng.wenwen.model.Question;
import com.xbcheng.wenwen.service.FollowService;
import com.xbcheng.wenwen.service.QuestionService;
import com.xbcheng.wenwen.service.UserService;
import com.xbcheng.wenwen.util.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ResultController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @GetMapping("/search")
    public String searchResult(@RequestParam("q")String keyword,Model model,
                        @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                        @RequestParam(value="pageSize", defaultValue="10") int pageSize){

        PageInfo<Question> pageInfo = questionService.searchQuestion(keyword,pageNum,pageSize);

        List<Map<String,Object>> questionVos = new ArrayList<>();

        for(Question question : pageInfo.getList()){
            Map<String,Object> vo = new HashMap<>();
            vo.put("question",question);
            vo.put("user",userService.findById(question.getUserId()));
            vo.put("followCount",followService.getFollowersCount(EntityType.ENTITY_QUESTION,question.getId()));
            questionVos.add(vo);
        }

        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("questionVos",questionVos);
        model.addAttribute("keyword",keyword);
        return "result";
    }

}
