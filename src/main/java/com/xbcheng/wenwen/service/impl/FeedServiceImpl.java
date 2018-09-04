package com.xbcheng.wenwen.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xbcheng.wenwen.mapper.FeedMapper;
import com.xbcheng.wenwen.model.Feed;
import com.xbcheng.wenwen.service.FeedService;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedMapper feedMapper;


    @Override
    public String addFeed(Feed feed) {
        if(feedMapper.insertSelective(feed)<1){
            return ResultUtil.fail();
        }

        return ResultUtil.success();
    }

    @Override
    public PageInfo<Feed> getUserFeeds(List<Integer> userIdList,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Feed> feeds = feedMapper.getUserFeeds(userIdList);
        PageInfo<Feed> pageInfo = new PageInfo<>(feeds);
        return pageInfo;
    }

    @Override
    public Feed findById(Integer id) {
        return feedMapper.selectByPrimaryKey(id);
    }

}
