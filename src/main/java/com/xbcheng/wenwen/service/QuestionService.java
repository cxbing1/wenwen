package com.xbcheng.wenwen.service;

import com.alibaba.fastjson.JSONObject;
import com.xbcheng.wenwen.model.Question;

import java.util.List;

public interface QuestionService {

    public String addQuestion(Question question);

    public Question selectById(int id);

    public List<Question> getQuestionList();

    public List<Question> getQuestionListByUserId(int userId);

    public List<Question> getByCondition(Question question);
}
