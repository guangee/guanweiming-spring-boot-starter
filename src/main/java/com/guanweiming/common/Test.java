package com.guanweiming.common;

import com.guanweiming.common.util.ip.JuHe;
import org.json.JSONObject;

/**
 * @author https://github.com/zziaguan/
 */
public class Test {
    public static void main(String[] args){
        JSONObject object = new JuHe().getResult("125.34.220.179");
        System.out.println(object);
    }
}
