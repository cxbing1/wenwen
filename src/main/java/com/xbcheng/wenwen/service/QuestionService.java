package com.xbcheng.wenwen.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xbcheng.wenwen.model.Question;

import java.util.List;

public interface QuestionService {

    public String addQuestion(Question question);

    public Question selectById(int id);

    public PageInfo<Question> getQuestionList(int pageNum, int pageSize);

    public List<Question> getQuestionListByUserId(int userId);

    public List<Question> getByCondition(Question question);

    public int deleteQuestion(Integer id);

    public PageInfo<Question> searchQuestion(String keyword,int pageNum,int pageSize);
}
