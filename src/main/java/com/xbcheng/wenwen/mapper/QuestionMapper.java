package com.xbcheng.wenwen.mapper;

import com.xbcheng.wenwen.model.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);
}