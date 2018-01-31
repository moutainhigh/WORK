package com.xlkfinance.bms.web.api.partnerapi.dr.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

public class RSAUtils {

    private static final String PUBLIC_KEY = "public";
    private static final String PRIVATE_KEY = "private";
    private static final String ALGORITHM = "RSA";

    public static Map<String, Object> getKeyMap() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    private static String encodeBase64(byte[] key) {
        return Base64.encodeBase64String(key);
    }

    private static byte[] decodeBase64(String key) {
        return Base64.decodeBase64(key);
    }

    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encodeBase64(key.getEncoded());
    }

    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encodeBase64(key.getEncoded());
    }

    /**
     * 公钥字符串获取publickey
     */
    public static RSAPublicKey getPublicKey(String pubKeyStr) {
        try {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(decodeBase64(pubKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(bobPubKeySpec);
            return publicKey;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public static RSAPrivateKey getPrivateKey(String priKeyStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(decodeBase64(priKeyStr));
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(priPKCS8);
            return privateKey;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public static String encrypt(String source, RSAPrivateKey privateKey)
            throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] b = source.getBytes("UTF-8");
        /** 执行加密操作 超过117字节报错 */
        byte[] dataReturn = null;
        for (int i = 0; i < b.length; i += 64) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(b, i, i + 64));
            dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
        }

        return encodeBase64(dataReturn);
    }

    public static String decrypt(String encryptSource, RSAPublicKey publicKey)
            throws Exception {

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] b = decodeBase64(encryptSource);
        byte[] dataReturn = null;
        for (int i = 0; i < b.length; i += 128) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(b, i, i + 128));
            dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
        }

        return new String(dataReturn, "utf-8");
    }
    
}
