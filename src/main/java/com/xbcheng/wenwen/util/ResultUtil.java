package com.xbcheng.wenwen.util;

import com.alibaba.fastjson.JSONObject;

public class ResultUtil {

    public static String success(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("msg","操作成功");
        return jsonObject.toJSONString();
    }

    public static String fail(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",1);
        jsonObject.put("msg","操作失败");
        return jsonObject.toJSONString();
    }

    public static String success(String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("msg",msg);
        return jsonObject.toJSONString();
    }

    public static String fail(String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",1);
        jsonObject.put("msg",msg);
        return jsonObject.toJSONString();
    }

    public static String fail(int code,String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        return jsonObject.toJSONString();
    }
}
