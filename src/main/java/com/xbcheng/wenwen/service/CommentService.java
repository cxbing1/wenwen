package com.xbcheng.wenwen.service;

import com.xbcheng.wenwen.model.Comment;

import java.util.List;

public interface CommentService {


    //增删改查service
    int deleteByPrimaryKey(Integer id);

    String addComment(Comment comment);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Comment comment);

    List<Comment> selectByEntity(int entityId, int entityType);
}
