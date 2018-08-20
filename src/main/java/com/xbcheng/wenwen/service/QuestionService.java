package com.xbcheng.wenwen.service;

import com.alibaba.fastjson.JSONObject;
import com.xbcheng.wenwen.model.Question;

public interface QuestionService {

    public String addQuestion(Question question);

    public Question selectById(int id);
}
