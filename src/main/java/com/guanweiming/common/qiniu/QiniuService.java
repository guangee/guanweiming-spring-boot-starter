package com.guanweiming.common.qiniu;

import com.qiniu.util.Auth;

/**
 * @author chezhu.xin
 */
public class QiniuService {
    private final String accessKey;
    private final String secretKey;
    private final String bucket;

    QiniuService(String accessKey, String secretKey, String bucket) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
    }

    public String uptoken(){
        return Auth.create(accessKey, secretKey).uploadToken(bucket);
    }
}
