package com.sttech.supervisor.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * function:
 * Created by 韦国旺 on 2017/5/15 0015.
 * Copyright (c) 2017 北京联龙博通 All Rights Reserved.
 */
public class StrUtils {

    /**
     * 验证手机号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        String PHONE_PATTERN = "^(1)\\d{10}$";
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
