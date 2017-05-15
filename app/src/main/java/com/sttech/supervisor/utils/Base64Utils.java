package com.sttech.supervisor.utils;

import android.util.Base64;

/**
 * function : base64工具类
 * Created by 韦国旺 on 2017/5/8.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class Base64Utils {

    /**
     * base64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBase64(String key) {
        return new String(Base64.encode(key.getBytes(), Base64.DEFAULT));
    }

//    protected String encodeBase64(String rawString) {
//
//        Base64 base64 = new Base64();
//
//        return base64.encodeToString(rawString.getBytes());
//
//    }


    /**
     * base64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBase64(String key) {
        return new String(Base64.decode(key.getBytes(), Base64.DEFAULT));
    }

}
