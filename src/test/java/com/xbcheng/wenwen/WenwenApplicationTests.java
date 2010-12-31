package com.xbcheng.wenwen;

import com.xbcheng.wenwen.async.EventModel;
import com.xbcheng.wenwen.mapper.CommentMapper;
import com.xbcheng.wenwen.mapper.QuestionMapper;
import com.xbcheng.wenwen.model.Comment;
import com.xbcheng.wenwen.model.Question;
import com.xbcheng.wenwen.util.EntityType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import java.io.Writer;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WenwenApplicationTests {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;
    @Test
    public void contextLoads() {

        List<Question> questionList = questionMapper.selectSelective(new Question());

        for(Question question :questionList){
            Comment comment = new Comment() ;
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setEntityId(question.getId());
            int size = commentMapper.selectSelective(comment).size();
            Question upQuestion = new Question();
            upQuestion.setId(question.getId());
            upQuestion.setCommentCount(size);
            questionMapper.updateByPrimaryKeySelective(upQuestion);
        }


    }

}
