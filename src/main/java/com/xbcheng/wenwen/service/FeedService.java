package com.xbcheng.wenwen.service;

import com.github.pagehelper.PageInfo;
import com.xbcheng.wenwen.model.Feed;

import java.util.List;

public interface FeedService {

    public String addFeed(Feed feed);

    public PageInfo<Feed> getUserFeeds(List<Integer> userIdList,int pageNum,int pageSize);

    public Feed findById(Integer id);
}
