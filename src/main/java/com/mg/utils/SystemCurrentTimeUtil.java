package com.mg.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemCurrentTimeUtil {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentTime() {
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    public static long getCurrentDate() {
        return System.currentTimeMillis();
    }
}
