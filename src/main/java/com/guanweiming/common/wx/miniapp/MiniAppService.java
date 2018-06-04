package com.guanweiming.common.wx.miniapp;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.annotations.SerializedName;
import com.guanweiming.common.JsonUtil;
import com.guanweiming.common.ServerResponse;
import com.guanweiming.common.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MiniAppService {
    private static final Cache<String, String> CACHE = CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .maximumSize(20000)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    private static final Cache<String, String> OPENID_CACHE = CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .maximumSize(20000)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();
    private final MiniAppProperties miniAppProperties;

    MiniAppService( MiniAppProperties miniAppProperties){
        this.miniAppProperties = miniAppProperties;
    }

    @Data
    public static class SessionKeyVo {
        @SerializedName("session_key")
        private String sessionKey;
        @SerializedName("openid")
        private String openid;
        @SerializedName("unionid")
        private String unionid;
        @SerializedName("unionId")
        private String unionId2;
    }


    public ServerResponse<SessionKeyVo> sessionKey(String code) throws JSONException {


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.weixin.qq.com/sns/jscode2session");
        stringBuilder.append("?").append("appid").append("=").append(miniAppProperties.getAppId());
        stringBuilder.append("&").append("secret").append("=").append(miniAppProperties.getAppSecret());
        stringBuilder.append("&").append("js_code").append("=").append(code);
        stringBuilder.append("&").append("grant_type").append("=").append("authorization_code");
        String result = new RestTemplate().getForObject(stringBuilder.toString(), String.class);
        log.info(stringBuilder.toString());
        log.info(result);

        String sessinKey = JsonUtil.toJsonObject(result).getString("session_key");
        String openid = JsonUtil.toJsonObject(result).getString("openid");
        log.debug("添加缓存");
        CACHE.put(code, sessinKey);
        OPENID_CACHE.put(code, openid);
        SessionKeyVo sessionKeyVo = JsonUtil.fromJson(result, SessionKeyVo.class);
        return ServerResponse.createBySuccess(sessionKeyVo);
    }

    public ServerResponse<SessionKeyVo> descrypt(String code, String appId, String encryptedData, String iv) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, JSONException {
        if (StringUtil.isBlank(code)) {
            return ServerResponse.createByErrorMessage("code 值不允许为空");
        }
        String sessionKey = CACHE.getIfPresent(code);
        if (StringUtil.isBlank(sessionKey)) {


            String stringBuilder = "https://api.weixin.qq.com/sns/jscode2session" +
                    "?" + "appid" + "=" + miniAppProperties.getAppId() +
                    "&" + "secret" + "=" + miniAppProperties.getAppSecret() +
                    "&" + "js_code" + "=" + code +
                    "&" + "grant_type" + "=" + "authorization_code";
            String resultStr = new RestTemplate().getForObject(stringBuilder, String.class);
            String str = JsonUtil.toJsonObject(resultStr).getString("session_key");
            String openid = JsonUtil.toJsonObject(resultStr).getString("openid");
            if (StringUtil.isNotBlank(str)) {
                sessionKey = str;
            }
            log.debug(resultStr);
            log.debug("查询的结果");
        }
        String result = WXBizDataCrypt.getInstance().decrypt(encryptedData, sessionKey, iv, "utf-8");
        SessionKeyVo sessionKeyVo = JsonUtil.fromJson(result, SessionKeyVo.class);
        return ServerResponse.createBySuccess(sessionKeyVo);
    }
}
