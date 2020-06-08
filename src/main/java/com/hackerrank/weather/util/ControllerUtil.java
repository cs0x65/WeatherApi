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
        String latitude = queryParams.get("latitude") != null ? queryParams.get("latitude") : queryParams.get("lat");
        String longitude = queryParams.get("longitude") != null ? queryParams.get("longitude") : queryParams.get("lon");
        String startDate = queryParams.get("startDate") != null ? queryParams.get("startDate") :
                queryParams.get("start");
        String endDate = queryParams.get("endDate") != null ? queryParams.get("endDate") : queryParams.get("end");

        if (latitude != null && longitude != null && startDate != null && endDate != null)
            return FILTER.DATE_RANGE_LOCATION_FILTER;

        if (startDate != null && endDate != null)
            return FILTER.DATE_RANGE_FILTER;

        if (latitude != null && longitude != null)
            return FILTER.LOCATION_FILTER;

        return FILTER.VOID_FILTER;
    }

    /**
     *
     * @param queryParams
     * @return array of String with lat @ index 0 while lon @ index 1
     */
    public static String[] getLocationLatLng(Map<String, String> queryParams){
        String latitude = queryParams.get("latitude") != null ? queryParams.get("latitude") : queryParams.get("lat");
        String longitude = queryParams.get("longitude") != null ? queryParams.get("longitude") : queryParams.get("lon");
        return new String[]{latitude, longitude};
    }

    /**
     *
     * @param queryParams
     * @return array of String with start @ index 0 while end @ index 1
     */
    public static String[] getStartAndEndDates(Map<String, String> queryParams){
        String startDate = queryParams.get("startDate") != null ? queryParams.get("startDate") :
                queryParams.get("start");
        String endDate = queryParams.get("endDate") != null ? queryParams.get("endDate") : queryParams.get("end");
        return new String[]{startDate, endDate};
    }
}
