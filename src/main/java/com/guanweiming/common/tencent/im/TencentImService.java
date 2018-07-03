package com.guanweiming.common.tencent.im;

import com.guanweiming.common.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.zip.DataFormatException;

@Slf4j
public class TencentImService {
    private static final String HOST = "https://console.tim.qq.com";

    private final TencentImUtil tencentImUtil;
    private final TencentImProperties tencentImProperties;

    public TencentImService(TencentImProperties tencentImProperties) {
        this.tencentImProperties = tencentImProperties;
        this.tencentImUtil = new TencentImUtil(tencentImProperties);
    }

    public int register(String username, String nickname, String avatar,String userSig) {
        String url = HOST + "/v4/im_open_login_svc/account_import?" +
                "usersig=" + userSig +
                "&identifier=admin&" +
                "sdkappid=" + tencentImProperties.getSdkAppId() +
                "&random=" + new Random().nextInt() +
                "&contenttype=json";

        JSONObject body = new JSONObject();
        body.put("Identifier", username);
        body.put("Nick", nickname);
        body.put("FaceUrl", avatar);


        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=UTF-8"), body.toString());


        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();

        okhttp3.Call call = okHttpClient.newCall(request);
        String result = null;
        try {
            okhttp3.Response response = call.execute();
            result = response.body().string();
            log.info(result);

            return JsonUtil.toJsonObject(result).getInt("ErrorCode");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("error:{}", e);
            return 1;
        }

    }

    public TencentImUtil.GenTLSSignatureResult GenTLSSignatureEx(String identifier) throws IOException {
        return tencentImUtil.GenTLSSignatureEx(identifier) ;
    }

    public TencentImUtil.CheckTLSSignatureResult CheckTLSSignatureEx(String urlSig, String identifier) throws DataFormatException {
        return tencentImUtil.CheckTLSSignatureEx(urlSig,identifier);
    }
}
