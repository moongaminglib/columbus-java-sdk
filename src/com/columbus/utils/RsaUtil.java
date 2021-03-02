package com.columbus.utils;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class RsaUtil {

    private String pubKey;

    public RsaUtil() {
        pubKey = readStringFromFile("java_public.pem");
    }

    /**
     * 使用共钥验证签名
     *
     * @param sign
     * @param src
     * @return
     */
    public boolean verifyByPublicKey(String sign, String src) {
        try {
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            byte[] pubbyte = base64decode(pubKey.trim());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);
            sigEng.initVerify(rsaPubKey);
            sigEng.update(src.getBytes());
            byte[] sign1 = base64decode(sign);
            return sigEng.verify(sign1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * base64加密
     *
     * @param bstr
     * @return
     */
    @SuppressWarnings("restriction")
    private String base64encode(byte[] bstr) {
        String str = new sun.misc.BASE64Encoder().encode(bstr);
        str = str.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
        return str;
    }

    /**
     * base64解密
     *
     * @param str
     * @return byte[]
     */
    @SuppressWarnings("restriction")
    private byte[] base64decode(String str) {
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bt;
    }

    /**
     * 从文件中读取所有字符串
     *
     * @param fileName
     * @return String
     */
    private String readStringFromFile(String fileName) {
        StringBuffer str = new StringBuffer();
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            char[] temp = new char[1024];
            while (fr.read(temp) != -1) {
                str.append(temp);
            }
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return str.toString();
    }


    public String mapToString(Map<String,String> params)
    {
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortedParams.entrySet();
        StringBuilder basestring = new StringBuilder();
        for (Entry<String, String> param : entrys) {
            if("_signature".equals(param.getKey().toLowerCase())){
                continue;
            }
            basestring.append(param.getKey()).append("=").append(param.getValue()).append("&");
        }
        if(basestring.length()> 1){
            basestring.deleteCharAt(basestring.length()-1);
        }
        //
        String macData = basestring.toString();

        return macData;
    }
}
