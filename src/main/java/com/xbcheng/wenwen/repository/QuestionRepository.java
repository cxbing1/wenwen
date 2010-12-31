package com.xbcheng.wenwen.repository;

import com.xbcheng.wenwen.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface QuestionRepository extends ElasticsearchRepository<Question,Integer> {
    public Page<Question> findByTitleAndContent(String title, Pageable pageable);
}


