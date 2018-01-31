package com.xlkfinance.bms.web.api.partnerapi.dr.util;

import java.security.MessageDigest;

public class SHAUtils {

    private static final String sha256 = "SHA-256";

    public static String encrypt(String source) throws Exception{

        MessageDigest md = MessageDigest.getInstance(sha256);
        md.update(source.getBytes("utf-8"));
        return bytes2HexString(md.digest());
    }
    
    private static String bytes2HexString(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
    
}
