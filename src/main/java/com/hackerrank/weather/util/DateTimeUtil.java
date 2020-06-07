package com.hackerrank.weather.util;

import java.text.SimpleDateFormat;

public class DateTimeUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public static SimpleDateFormat getDateFormat(){
        return dateFormat;
    }
}
