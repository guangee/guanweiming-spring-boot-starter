package com.guanweiming.common.util.ip;

import com.guanweiming.common.util.IpUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

/**
 * @author https://github.com/zziaguan/
 */
public class TaoBao implements IpUtil {

    public JSONObject getResult(String ip) {
        String requestUrl = "http://ip.taobao.com/service/getIpInfo2.php?ip=" + ip;
        String result = new RestTemplate().getForObject(requestUrl, String.class);
        JSONObject object;
        try {
            object = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return object;
    }
}
