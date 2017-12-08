package com.guanweiming.common;

import com.google.common.collect.Maps;
import com.guanweiming.common.util.RateUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author https://github.com/zziaguan/
 */
public class Test {
    public static void main(String[] args) throws Exception {
        RateUtil<String> util = new RateUtil<String>();
        List<RateUtil.RateItem<String>> list=new LinkedList<RateUtil.RateItem<String>>();
        list.add(new RateUtil.RateItem<String>(1,"苹果"));
        list.add(new RateUtil.RateItem<String>(2,"橘子"));
        list.add(new RateUtil.RateItem<String>(3,"香蕉"));
        Map<String,Integer> map = Maps.newHashMap();
        for (int i = 0; i < 1000000; i++) {
            String result = util.getRate(list);
            if(map.get(result)!=null){
                map.put(result,map.get(result)+1);
            }else {
                map.put(result,1);
            }
        }
        System.out.println(map);
    }
}
