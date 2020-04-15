package com.guanweiming.common.aliyun.carno;

import com.guanweiming.common.utils.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author guanweiming
 */
@Slf4j
public class AliYunCarNoService {

    private final AliYunCarNoProperties aliYunCarNoProperties;

    public AliYunCarNoService(AliYunCarNoProperties aliYunCarNoProperties) {
        this.aliYunCarNoProperties = aliYunCarNoProperties;
    }


    public ServerResponse<String> ocr(String base64Str) {
        String url = "https://dm-53.data.aliyun.com/rest/160601/ocr/ocr_vehicle.json";

        String appCode = aliYunCarNoProperties.getAppCode();

        JSONObject configObj = new JSONObject();
        configObj.put("side", "face");

        JSONObject body = new JSONObject();
        body.put("image", base64Str);
        body.put("configure", configObj);


        log.debug(body.toString());
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=UTF-8"), body.toString());


        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "APPCODE " + appCode)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();

        okhttp3.Call call = okHttpClient.newCall(request);
        String result = null;
        try {
            okhttp3.Response response = call.execute();
            result = response.body().string();
            log.debug(result);
            log.debug("hello");
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("解析失败：" + e.getMessage());
        }
        return ServerResponse.createBySuccess(result);
    }
}
