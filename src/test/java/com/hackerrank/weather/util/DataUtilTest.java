package com.hackerrank.weather.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DataUtilTest {

    @Test
    public void testBuildTemperatureReturnsArray(){
        Float[] temperatures = DataUtil.buildTemperature("10, 20,30");
        for (int i = 0; i < temperatures.length; i++) {
            assertArrayEquals(new Float[]{10.0f, 20.0f, 30.0f}, temperatures);
        }
    }
}