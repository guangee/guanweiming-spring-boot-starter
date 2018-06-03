package com.guanweiming.common.aliyun.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.guanweiming.common.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.swing.text.html.FormSubmitEvent;
import java.util.Map;

/**
 * @author guanweiming
 */
@Slf4j
public class AliYunSmsService {
    private final AliYunSmsProperties aliYunSmsProperties;

    AliYunSmsService(AliYunSmsProperties aliYunSmsProperties) {
        this.aliYunSmsProperties = aliYunSmsProperties;
    }

    public String sendMessage(String phoneNumber, Map<String, Object> param) throws ClientException {
        if (StringUtil.isBlank(phoneNumber)) {
            return "手机号不允许为空";
        }
        if (param == null) {
            return "参数不允许";
        }
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";
        final String accessKeyId = aliYunSmsProperties.getAccessKey();
        final String accessKeySecret = aliYunSmsProperties.getSecretKey();
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(phoneNumber);
        request.setSignName(aliYunSmsProperties.getSignName());
        request.setTemplateCode(aliYunSmsProperties.getTemplateCode());
        JSONObject object = new JSONObject(param);
        log.debug(object.toString());
        request.setTemplateParam(object.toString());
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse.getCode();
    }
}
