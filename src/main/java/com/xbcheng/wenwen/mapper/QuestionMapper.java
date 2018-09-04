package com.xbcheng.wenwen.mapper;

import com.xbcheng.wenwen.model.Message;
import com.xbcheng.wenwen.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);

    List<Question> selectSelective(Question question);

    void updateCommentCount(@Param("id") Integer id,@Param("value") Integer value);

    List<Question> searchQuestion(@Param("keyword")String keyword);
}