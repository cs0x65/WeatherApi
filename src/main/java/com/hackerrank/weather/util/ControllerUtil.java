package com.hackerrank.weather.util;

import java.util.Map;

public class ControllerUtil {
    public static enum FILTER{
        VOID_FILTER,
        DATE_RANGE_FILTER,
        LOCATION_FILTER,
        DATE_RANGE_LOCATION_FILTER
    }

    public static FILTER getFilter(Map<String, String> queryParams){
        String latitude = queryParams.get("latitude");
        String longitude = queryParams.get("longitude");
        String startDate = queryParams.get("startDate");
        String endDate = queryParams.get("endDate");

        if (latitude != null && longitude != null && startDate != null && endDate != null)
            return FILTER.DATE_RANGE_LOCATION_FILTER;

        if (startDate != null && endDate != null)
            return FILTER.DATE_RANGE_FILTER;

        if (latitude != null && longitude != null)
            return FILTER.LOCATION_FILTER;

        return FILTER.VOID_FILTER;
    }
}
