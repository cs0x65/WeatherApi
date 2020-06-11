package com.hackerrank.weather.dto.adapter;

import com.hackerrank.weather.util.DateTimeUtil;
import com.hackerrank.weather.dto.WeatherDTO;
import com.hackerrank.weather.model.Weather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.Arrays;

public class WeatherRequestDTOAdapter implements RequestDTOAdapter<WeatherDTO, Weather>{
    Logger logger = LogManager.getLogger(getClass().getName());

    @Override
    public Weather build(WeatherDTO dto) {
        Weather weather = new Weather();
        weather.setId(dto.getId());
        weather.setLocation(new LocationRequestAdapter().build(dto.getLocation()));
        Float[] temperatures = dto.getTemperature();
        Arrays.sort(temperatures);
        weather.setTemperature(buildTemperature(temperatures));
        weather.setLowestTemp(temperatures[0]);
        weather.setHighestTemp(temperatures[temperatures.length-1]);
        try {
            weather.setDateRecorded(DateTimeUtil.getDateFormat().parse(dto.getDate()));
        } catch (ParseException e) {
            logger.error("Error parsing date: "+e);
            return null;
        }
        return weather;
    }

    private String buildTemperature(Float[] temperature){
        if (temperature == null || temperature.length < 1)
            return "";

        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(temperature).forEach(temp -> {
            stringBuilder.append(temp+",");
        });
        String temp = stringBuilder.toString();
        return temp.substring(0, temp.length()-1);
    }
}
