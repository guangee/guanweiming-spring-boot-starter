package com.guanweiming.common.tencent.im;

import com.ubl360.car.common.Base64Util;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.json.JSONObject;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class TencentImUtil {
    private final TencentImProperties tencentImProperties;

    public TencentImUtil(TencentImProperties tencentImProperties) {
        this.tencentImProperties = tencentImProperties;
    }

    public static class GenTLSSignatureResult {
        public String errMessage;
        public String urlSig;
        public int expireTime;
        public int initTime;

        public GenTLSSignatureResult() {
            errMessage = "";
            urlSig = "";
        }
    }

    public static class CheckTLSSignatureResult {
        public String errMessage;
        public boolean verifyResult;
        public int expireTime;
        public int initTime;

        public CheckTLSSignatureResult() {
            errMessage = "";
            verifyResult = false;
        }
    }

    /**
     * @param expire      有效期，单位是秒，推荐一个月
     * @param strAppid3rd 填写与 sdkAppid 一致字符串形式的值
     * @param skdAppid    应用的 appid
     * @param identifier  用户 id
     * @param accountType 创建应用后在配置页面上展示的 acctype
     * @param privStr     生成 tls 票据使用的私钥内容
     * @return 如果出错，GenTLSSignatureResult 中的 urlSig为空，errMsg 为出错信息，成功返回有效的票据
     * @throws IOException
     * @brief 生成 tls 票据
     */
    @Deprecated
    public GenTLSSignatureResult GenTLSSignature(long expire, String strAppid3rd, long skdAppid,
                                                 String identifier, long accountType, String privStr) throws IOException {

        GenTLSSignatureResult result = new GenTLSSignatureResult();

        Security.addProvider(new BouncyCastleProvider());
        Reader reader = new CharArrayReader(privStr.toCharArray());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PEMParser parser = new PEMParser(reader);
        Object obj = parser.readObject();
        parser.close();
        PrivateKey privKeyStruct = converter.getPrivateKey((PrivateKeyInfo) obj);

        // Create Json string and serialization String
        String jsonString = "{" + "\"TLS.account_type\":\"" + accountType + "\"," + "\"TLS.identifier\":\"" + identifier
                + "\"," + "\"TLS.appid_at_3rd\":\"" + strAppid3rd + "\"," + "\"TLS.sdk_appid\":\"" + skdAppid + "\","
                + "\"TLS.expire_after\":\"" + expire + "\"" + "}";
        // System.out.println("#jsonString : \n" + jsonString);

        String time = String.valueOf(System.currentTimeMillis() / 1000);
        String SerialString = "TLS.appid_at_3rd:" + strAppid3rd + "\n" + "TLS.account_type:" + accountType + "\n"
                + "TLS.identifier:" + identifier + "\n" + "TLS.sdk_appid:" + skdAppid + "\n" + "TLS.time:" + time + "\n"
                + "TLS.expire_after:" + expire + "\n";

        // System.out.println("#SerialString : \n" + SerialString);
        // System.out.println("#SerialString Hex: \n" +
        // Hex.encodeHexString(SerialString.getBytes()));

        try {
            // Create Signature by SerialString
            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initSign(privKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
            byte[] signatureBytes = signature.sign();

            String sigTLS = Base64.encodeBase64String(signatureBytes);
            // System.out.println("#sigTLS : " + sigTLS);

            // Add TlsSig to jsonString
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("TLS.sig", (Object) sigTLS);
            jsonObject.put("TLS.time", (Object) time);
            jsonString = jsonObject.toString();

            // System.out.println("#jsonString : \n" + jsonString);

            // compression
            Deflater compresser = new Deflater();
            compresser.setInput(jsonString.getBytes(Charset.forName("UTF-8")));

            compresser.finish();
            byte[] compressBytes = new byte[512];
            int compressBytesLength = compresser.deflate(compressBytes);
            compresser.end();
            // System.out.println("#compressBytes "+ compressBytesLength+": " +
            // Hex.encodeHexString(Arrays.copyOfRange(compressBytes,0,compressBytesLength)));

            // String userSig =
            // Base64.encodeBase64URLSafeString(Arrays.copyOfRange(compressBytes,0,compressBytesLength));
            String userSig = new String(
                    Base64Util.base64EncodeUrl(Arrays.copyOfRange(compressBytes, 0, compressBytesLength)));

            result.urlSig = userSig;
            // System.out.println("urlSig: "+ userSig);
        } catch (Exception e) {
            e.printStackTrace();
            result.errMessage = "generate usersig failed";
        }

        return result;
    }

    /**
     * @param urlSig      返回 tls 票据
     * @param strAppid3rd 填写与 sdkAppid 一致的字符串形式的值
     * @param skdAppid    应的 appid
     * @param identifier  用户 id
     * @param accountType 创建应用后在配置页面上展示的 acctype
     * @param publicKey   用于校验 tls 票据的公钥内容，但是需要先将公钥文件转换为 java 原生 api 使用的格式，下面是推荐的命令 openssl
     *                    pkcs8 -topk8 -in ec_key.pem -outform PEM -out p8_priv.pem -nocrypt
     * @return 如果出错 CheckTLSSignatureResult 中的 verifyResult 为 false，错误信息在
     * errMsg，校验成功为 true
     * @throws DataFormatException
     * @brief 校验 tls 票据
     */
    @Deprecated
    public CheckTLSSignatureResult CheckTLSSignature(String urlSig, String strAppid3rd, long skdAppid,
                                                     String identifier, long accountType, String publicKey) throws DataFormatException {
        CheckTLSSignatureResult result = new CheckTLSSignatureResult();
        Security.addProvider(new BouncyCastleProvider());

        // DeBaseUrl64 urlSig to json
        Base64 decoder = new Base64();

        // byte [] compressBytes = decoder.decode(urlSig.getBytes());
        byte[] compressBytes = Base64Util.base64DecodeUrl(urlSig.getBytes(Charset.forName("UTF-8")));

        // System.out.println("#compressBytes Passing in[" + compressBytes.length + "] "
        // + Hex.encodeHexString(compressBytes));

        // Decompression
        Inflater decompression = new Inflater();
        decompression.setInput(compressBytes, 0, compressBytes.length);
        byte[] decompressBytes = new byte[1024];
        int decompressLength = decompression.inflate(decompressBytes);
        decompression.end();

        String jsonString = new String(Arrays.copyOfRange(decompressBytes, 0, decompressLength));

        // System.out.println("#Json String passing in : \n" + jsonString);

        // Get TLS.Sig from json
        JSONObject jsonObject = new JSONObject(jsonString);
        String sigTLS = jsonObject.getString("TLS.sig");

        // debase64 TLS.Sig to get serailString
        byte[] signatureBytes = decoder.decode(sigTLS.getBytes(Charset.forName("UTF-8")));

        try {

            String sigTime = jsonObject.getString("TLS.time");
            String sigExpire = jsonObject.getString("TLS.expire_after");

            // checkTime
            // System.out.println("#time check: "+ System.currentTimeMillis()/1000 + "-"
            // + Long.parseLong(sigTime) + "-" + Long.parseLong(sigExpire));
            if (System.currentTimeMillis() / 1000 - Long.parseLong(sigTime) > Long.parseLong(sigExpire)) {
                result.errMessage = new String("TLS sig is out of date ");
                System.out.println("Timeout");
                return result;
            }

            // Get Serial String from json
            String SerialString = "TLS.appid_at_3rd:" + strAppid3rd + "\n" + "TLS.account_type:" + accountType + "\n"
                    + "TLS.identifier:" + identifier + "\n" + "TLS.sdk_appid:" + skdAppid + "\n" + "TLS.time:" + sigTime
                    + "\n" + "TLS.expire_after:" + sigExpire + "\n";

            // System.out.println("#SerialString : \n" + SerialString);

            Reader reader = new CharArrayReader(publicKey.toCharArray());
            PEMParser parser = new PEMParser(reader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            Object obj = parser.readObject();
            parser.close();
            PublicKey pubKeyStruct = converter.getPublicKey((SubjectPublicKeyInfo) obj);

            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initVerify(pubKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
            boolean bool = signature.verify(signatureBytes);
            // System.out.println("#jdk ecdsa verify : " + bool);
            result.verifyResult = bool;
        } catch (Exception e) {
            e.printStackTrace();
            result.errMessage = "Failed in checking sig";
        }

        return result;
    }

    /**
     * @param identifier 用户 id
     *                   私钥文件内容
     * @return
     * @throws IOException
     * @brief 生成 tls 票据，精简参数列表，有效期默认为 180 天
     */
    public GenTLSSignatureResult GenTLSSignatureEx(String identifier)
            throws IOException {
        return GenTLSSignatureEx(identifier, tencentImProperties.getPrivateString(), 3600 * 24 * 180);
    }

    /**
     * @param identifier 用户 id
     * @param privStr    私钥文件内容
     * @param expire     有效期，以秒为单位，推荐时长一个月
     * @return
     * @throws IOException
     * @brief 生成 tls 票据，精简参数列表
     */
    public GenTLSSignatureResult GenTLSSignatureEx(String identifier, String privStr, long expire)
            throws IOException {

        GenTLSSignatureResult result = new GenTLSSignatureResult();

        Security.addProvider(new BouncyCastleProvider());
        System.out.println(privStr);
        Reader reader = new CharArrayReader(privStr.toCharArray());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PEMParser parser = new PEMParser(reader);
        Object obj = parser.readObject();
        parser.close();
        PrivateKey privKeyStruct = converter.getPrivateKey((PrivateKeyInfo) obj);

        String jsonString = "{" + "\"TLS.account_type\":\"" + 0 + "\"," + "\"TLS.identifier\":\"" + identifier + "\","
                + "\"TLS.appid_at_3rd\":\"" + 0 + "\"," + "\"TLS.sdk_appid\":\"" + tencentImProperties.getSdkAppId() + "\","
                + "\"TLS.expire_after\":\"" + expire + "\"," + "\"TLS.version\": \"201512300000\"" + "}";

        String time = String.valueOf(System.currentTimeMillis() / 1000);
        String SerialString = "TLS.appid_at_3rd:" + 0 + "\n" + "TLS.account_type:" + 0 + "\n" + "TLS.identifier:"
                + identifier + "\n" + "TLS.sdk_appid:" + tencentImProperties.getSdkAppId() + "\n" + "TLS.time:" + time + "\n"
                + "TLS.expire_after:" + expire + "\n";

        try {
            // Create Signature by SerialString
            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initSign(privKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
            byte[] signatureBytes = signature.sign();

            String sigTLS = Base64.encodeBase64String(signatureBytes);

            // Add TlsSig to jsonString
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("TLS.sig", (Object) sigTLS);
            jsonObject.put("TLS.time", (Object) time);
            jsonString = jsonObject.toString();

            // compression
            Deflater compresser = new Deflater();
            compresser.setInput(jsonString.getBytes(Charset.forName("UTF-8")));

            compresser.finish();
            byte[] compressBytes = new byte[512];
            int compressBytesLength = compresser.deflate(compressBytes);
            compresser.end();
            String userSig = new String(
                    Base64Util.base64EncodeUrl(Arrays.copyOfRange(compressBytes, 0, compressBytesLength)));

            result.urlSig = userSig;
        } catch (Exception e) {
            e.printStackTrace();
            result.errMessage = "generate usersig failed";
        }

        return result;
    }

    public CheckTLSSignatureResult CheckTLSSignatureEx(String urlSig, String identifier) throws DataFormatException {

        CheckTLSSignatureResult result = new CheckTLSSignatureResult();
        Security.addProvider(new BouncyCastleProvider());

        // DeBaseUrl64 urlSig to json
        Base64 decoder = new Base64();

        byte[] compressBytes = Base64Util.base64DecodeUrl(urlSig.getBytes(Charset.forName("UTF-8")));

        // Decompression
        Inflater decompression = new Inflater();
        decompression.setInput(compressBytes, 0, compressBytes.length);
        byte[] decompressBytes = new byte[1024];
        int decompressLength = decompression.inflate(decompressBytes);
        decompression.end();

        String jsonString = new String(Arrays.copyOfRange(decompressBytes, 0, decompressLength));

        // Get TLS.Sig from json
        JSONObject jsonObject = new JSONObject(jsonString);
        String sigTLS = jsonObject.getString("TLS.sig");

        // debase64 TLS.Sig to get serailString
        byte[] signatureBytes = decoder.decode(sigTLS.getBytes(Charset.forName("UTF-8")));

        try {
            String strSdkAppid = jsonObject.getString("TLS.sdk_appid");
            String sigTime = jsonObject.getString("TLS.time");
            String sigExpire = jsonObject.getString("TLS.expire_after");

            if (Integer.parseInt(strSdkAppid) != tencentImProperties.getSdkAppId()) {
                result.errMessage = new String(
                        "sdkappid " + strSdkAppid + " in tls sig not equal sdkappid " + tencentImProperties.getSdkAppId() + " in request");
                return result;
            }

            if (System.currentTimeMillis() / 1000 - Long.parseLong(sigTime) > Long.parseLong(sigExpire)) {
                result.errMessage = new String("TLS sig is out of date");
                return result;
            }

            // Get Serial String from json
            String SerialString = "TLS.appid_at_3rd:" + 0 + "\n" + "TLS.account_type:" + 0 + "\n" + "TLS.identifier:"
                    + identifier + "\n" + "TLS.sdk_appid:" + tencentImProperties.getSdkAppId() + "\n" + "TLS.time:" + sigTime + "\n"
                    + "TLS.expire_after:" + sigExpire + "\n";

            Reader reader = new CharArrayReader(tencentImProperties.getPublicString().toCharArray());
            PEMParser parser = new PEMParser(reader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            Object obj = parser.readObject();
            parser.close();
            PublicKey pubKeyStruct = converter.getPublicKey((SubjectPublicKeyInfo) obj);

            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initVerify(pubKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
            boolean bool = signature.verify(signatureBytes);
            result.expireTime = Integer.parseInt(sigExpire);
            result.initTime = Integer.parseInt(sigTime);
            result.verifyResult = bool;
        } catch (Exception e) {
            e.printStackTrace();
            result.errMessage = "Failed in checking sig";
        }

        return result;
    }
}
