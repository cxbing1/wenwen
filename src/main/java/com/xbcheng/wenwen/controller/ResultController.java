package com.xbcheng.wenwen.controller;

import com.github.pagehelper.PageInfo;
import com.xbcheng.wenwen.model.Question;
import com.xbcheng.wenwen.repository.QuestionRepository;
import com.xbcheng.wenwen.service.FollowService;
import com.xbcheng.wenwen.service.QuestionService;
import com.xbcheng.wenwen.service.UserService;
import com.xbcheng.wenwen.util.EntityType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/search")
    public String searchResult(@RequestParam("q")String keyword,Model model,
                        @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                        @RequestParam(value="pageSize", defaultValue="10") int pageSize){





        PageRequest pages = new PageRequest(pageNum-1,pageSize);
        Question questionTest = questionRepository.findById(1000).get();
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title",keyword);
        Page<Question> questionPage = questionRepository.search(queryBuilder,pages);


        Map<String,Object> pageInfo = new HashMap<>();
        pageInfo.put("pages",questionPage.getTotalPages());
        pageInfo.put("pageNum",questionPage.getNumber()+1);
        pageInfo.put("isFirstPage",questionPage.getNumber()==0);
        pageInfo.put("isLastPage",questionPage.getNumber()==questionPage.getTotalPages()-1);
        pageInfo.put("prePage",questionPage.getNumber());
        pageInfo.put("nextPage",questionPage.getNumber()+2);

        List<Map<String,Object>> questionVos = new ArrayList<>();

        for(Question searchQuestion : questionPage.getContent()){
            Question question = questionService.selectById(searchQuestion.getId());
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
