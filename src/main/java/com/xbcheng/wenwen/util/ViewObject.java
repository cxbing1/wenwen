package com.xbcheng.wenwen.util;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {

    private Map<String,Object> map = new HashMap<>();

    public void put(String s,Object value){
        map.put(s,value);
    }

    public Object get(String s){
        return map.get(s);
    }

}
