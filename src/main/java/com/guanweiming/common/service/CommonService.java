package com.guanweiming.common.service;

import com.guanweiming.common.properties.CommonProperties;
import com.guanweiming.common.util.IpUtil;
import com.guanweiming.common.util.ip.JuHe;
import com.guanweiming.common.util.ip.TaoBao;
import org.json.JSONObject;

/**
 * @author https://github.com/zziaguan/
 */
public class CommonService {
    private IpUtil ipUtil;
    public CommonService(CommonProperties commonProperties) {
        if(commonProperties.getIp().equals("juhe")){
            ipUtil = new JuHe();
        }else if(commonProperties.getIp().equals("taobao")){
            ipUtil = new TaoBao();
        }else{
            ipUtil = new TaoBao();
        }
    }
    public JSONObject findIpInfo(String ip){
        return ipUtil.getResult(ip);
    }
}
