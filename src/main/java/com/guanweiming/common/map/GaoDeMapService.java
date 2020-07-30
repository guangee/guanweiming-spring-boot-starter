package com.guanweiming.common.map;

import com.google.common.collect.Lists;
import com.guanweiming.common.utils.JsonUtil;
import com.guanweiming.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author guanweiming
 */
@Slf4j
public class GaoDeMapService {
    private static int index = 0;
    private final List<String> keyList;

    public GaoDeMapService(GaoDeMapProperties gaoDeMapProperties) {
        keyList = Lists.newLinkedList(gaoDeMapProperties.getKey());
    }


    public synchronized String getGaoDeMapKey() {
        index = (index + 1) % keyList.size();
        String key = keyList.get(index);
        log.debug("gaoDeKey:\t{}\t{}",keyList.size(), key);
        return key;
    }

    /**
     * 使用高德地图进行逆地理位置解析
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 逆地理位置解析结果
     */
    public Result<Map> reGeo(double latitude, double longitude) {
        latitude = ((int) (latitude * 1000000)) / 1000000.0;
        longitude = ((int) (longitude * 1000000)) / 1000000.0;

        String url = "http://restapi.amap.com/v3/geocode/regeo" +
                "?location=" + longitude + "," + latitude +
                "&key=" + getGaoDeMapKey();
        String result = new RestTemplate().getForObject(url, String.class);
        log.debug(result);
        Map map = JsonUtil.fromJson(result, Map.class);
        JSONObject object = JsonUtil.toJsonObject(result);
        if ("OK".equals(object.getString("info"))) {
            return Result.createBySuccess(map);
        }

        return Result.createByErrorMessage("错误");
    }
}
