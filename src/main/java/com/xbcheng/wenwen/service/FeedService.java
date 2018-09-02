package com.xbcheng.wenwen.service;

import com.xbcheng.wenwen.model.Feed;

import java.util.List;

public interface FeedService {

    public String addFeed(Feed feed);

    public List<Feed> getUserFeeds(List<Integer> userIdList);

    public Feed findById(Integer id);
}
