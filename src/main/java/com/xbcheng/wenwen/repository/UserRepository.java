package com.xbcheng.wenwen.repository;

import com.xbcheng.wenwen.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends ElasticsearchRepository<User,Integer> {
}
