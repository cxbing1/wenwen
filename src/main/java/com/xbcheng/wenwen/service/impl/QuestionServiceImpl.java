package com.xbcheng.wenwen.service.impl;

import com.xbcheng.wenwen.mapper.QuestionMapper;
import com.xbcheng.wenwen.model.Question;
import com.xbcheng.wenwen.service.QuestionService;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import org.unbescape.html.HtmlEscape;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public String addQuestion(Question question) {
        //html过滤
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));

        if(questionMapper.insertSelective(question)>1){
            return ResultUtil.fail();
        }

        return ResultUtil.success();
    }

    @Override
    public Question selectById(int id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Question> getQuestionList() {
        return questionMapper.selectSelective(new Question());
    }

    @Override
    public List<Question> getQuestionListByUserId(int userId) {
        Question question = new Question();
        question.setUserId(userId);
        return questionMapper.selectSelective(question);
    }

    @Override
    public List<Question> getByCondition(Question question) {
        return questionMapper.selectSelective(question);
    }
}
