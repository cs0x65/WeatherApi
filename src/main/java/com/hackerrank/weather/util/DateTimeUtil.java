package com.hackerrank.weather.util;

import java.text.SimpleDateFormat;

public class DateTimeUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat getDateFormat(){
        return dateFormat;
    }
}
