package com.xbcheng.wenwen.service;

import com.xbcheng.wenwen.model.Comment;

import java.util.List;

public interface CommentService {


    //增删改查service
    int deleteByPrimaryKey(Integer id);

    String addComment(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectByEntity(int entityId, int entityType);
}
