package com.guanweiming.common.utils;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


public class JsonUtilTest {

    @Data
    private static class Demo {
        private LocalDateTime localDateTime;
    }

    @Test
    public void create() {
        Demo demo=new Demo();
        demo.setLocalDateTime(LocalDateTime.now());
        String json = JsonUtil.toJson(demo);
        System.out.println(json);
        demo = JsonUtil.fromJson(json, Demo.class);
        System.out.println(JsonUtil.toJson(demo,true));
    }
}
