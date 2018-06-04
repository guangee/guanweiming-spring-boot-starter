package com.guanweiming.common.wx.miniapp;

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

public class WXBizDataCrypt {
    private static WXBizDataCrypt instance = new WXBizDataCrypt();

    public static WXBizDataCrypt getInstance() {
        return instance;
    }

    private WXBizDataCrypt() {

    }

    /**
     * 对于官方加密数据（encryptData）解密说明如下： 加密数据解密算法 接口如果涉及敏感数据（如wx.getUserInfo当中的
     * openId 和unionId ），接口的明文内容将不包含这些敏感数据。开发者如需要获取敏感数据，需要对接口返回的加密数据(
     * encryptedData )进行对称解密。 解密算法如下： 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。
     * 对称解密的目标密文为 Base64_Decode(encryptedData), 对称解密秘钥 aeskey =
     * Base64_Decode(session_key), aeskey 是16字节 对称解密算法初始向量 iv 会在数据接口中返回。
     *
     * @param encryptedData  *            加密内容
     *                       *
     * @param sessionKey     *            小程序登录sessionKey
     *                       *
     * @param iv             *            解密算法初始向量 iv 会在数据接口中返回。
     *                       *
     * @param encodingFormat *            编码格式默认UTF-8
     *                       *
     * @return 返回解密后的字符串
     * *
     * *
     */
    public String decrypt(String encryptedData, String sessionKey, String iv, String encodingFormat) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(sessionKey), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            byte[] bytes = decode(original);
            return new String(bytes, Charset.forName(encodingFormat));
        } catch (Exception e) {
            return "";
        }
    }

    private byte[] decode(byte[] decrypted) {
        int pad = decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }
}