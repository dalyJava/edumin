package com.daly.edumin.basic.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daly on 2018/7/19.
 */
public class VueUtils {

    public static Map<String,String> getMapByListMap(String jsonStr){
        Map<String,String> returnMap  = new HashMap<>();
        if(StrUtil.isEmpty(jsonStr))
            return null;
        List<Map<String,JSONArray>> columnFilters = (List<Map<String,JSONArray>>) JSONObject.parse(jsonStr);
        for (Map<String,JSONArray> map : columnFilters){
            for(String keyStr : map.keySet()){
                String  valueStr = map.get(keyStr).toString();
                returnMap.put(keyStr,valueStr.substring(1,valueStr.length()-1));
            }
        }
        return returnMap;
    }

    /**
     * 根据list返回对应的VUE属性filters 所需的类型
     * @param filterMap
     * @return
     *////String[] filterNameArr,List<String> //filterValueList
    public static Map<String,List<Map<String,String>>> getFilters(Map<String,String[]> filterMap){
        Map<String,List<Map<String,String>>> filters = new HashMap<>();
        for(Map.Entry<String,String[]> entry : filterMap.entrySet()){
            //entry.getKey() entry.getValue
            List<Map<String,String>> valueList = new ArrayList<>();
            for (String str : entry.getValue()){
                Map<String,String> hashMap = new HashMap<>();
                hashMap.put("label",str);
                hashMap.put("value",str);
                valueList.add(hashMap);
            }
            filters.put(entry.getKey(),valueList);
        }
        return filters;
    }
}
