package com.hackerrank.weather.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DTOUtil {
    public static Float[] buildTemperature(String str){
        if (str == null)
            return new Float[]{};

        String[] temperature = str.split(",");
        return Arrays.stream(temperature)
                .map(tempStr -> Float.parseFloat(tempStr))
                .collect(Collectors.toList())
                .toArray(new Float[temperature.length]);
    }
}
