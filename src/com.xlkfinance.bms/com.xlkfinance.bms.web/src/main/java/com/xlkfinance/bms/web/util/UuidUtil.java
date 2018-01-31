package com.xlkfinance.bms.web.util;

import java.util.UUID;


/**
 * uuid Util
 * 
 * @author chenzhuzhen
 * @date 2017年2月15日 上午10:49:12
 */
public class UuidUtil {

    /**
     * 产生一个36个字符的UUID
     *
     * @return UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }
}
