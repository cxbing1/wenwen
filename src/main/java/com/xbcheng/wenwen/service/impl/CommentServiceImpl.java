package com.xbcheng.wenwen.service.impl;

import com.xbcheng.wenwen.mapper.CommentMapper;
import com.xbcheng.wenwen.model.Comment;
import com.xbcheng.wenwen.service.CommentService;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public String addComment(Comment comment) {
        if(commentMapper.insertSelective(comment)<1)
            return ResultUtil.fail();
        return ResultUtil.success();

    }

    @Override
    public Comment selectByPrimaryKey(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Comment comment) {
        return commentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public List<Comment> selectByEntity(int entityId, int entityType) {
        return commentMapper.selectByEntity(entityId,entityType);
    }
}
