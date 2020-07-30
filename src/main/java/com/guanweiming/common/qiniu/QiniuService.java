package com.guanweiming.common.qiniu;

import com.google.gson.Gson;
import com.guanweiming.common.utils.QiniuResponse;
import com.guanweiming.common.utils.StringUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * @author chezhu.xin
 */
@Slf4j
public class QiniuService {
    private final QiNiuProperties qiNiuProperties;

    QiniuService(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
    }

    public String uptoken() {
        return Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey()).uploadToken(qiNiuProperties.getBucket());
    }

    public QiniuResponse qiniuResponse() {
        String token = uptoken();
        if (StringUtil.isBlank(token)) {
            return QiniuResponse.createByError();
        }
        return QiniuResponse.createBySuccess(token);
    }

    public String upload(String name, InputStream inputStream, Zone zone) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String upToken = uptoken();
        try {
            Response response = uploadManager.put(inputStream, name, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.debug(putRet.key);
            log.debug(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.debug(r.toString());
            return null;
        }
        return qiNiuProperties.getHost() + name;
    }
}
