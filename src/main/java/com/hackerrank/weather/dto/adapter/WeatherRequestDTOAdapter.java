package com.hackerrank.weather.dto.adapter;

import com.hackerrank.weather.util.DateTimeUtil;
import com.hackerrank.weather.dto.WeatherDTO;
import com.hackerrank.weather.model.Weather;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.Arrays;

public class WeatherRequestDTOAdapter implements RequestDTOAdapter<WeatherDTO, Weather>{
    @Override
    public Weather build(WeatherDTO dto) {
        Weather weather = new Weather();
        weather.setId(dto.getId());
        weather.setLocation(new LocationRequestAdapter().build(dto.getLocation()));
        weather.setTemperature(buildTemperature(dto.getTemperature()));
        try {
            weather.setDateRecorded(DateTimeUtil.getDateFormat().parse(dto.getDate()));
        } catch (ParseException e) {
            Logger.getLogger(getClass()).error("Error parsing date: "+e);
            return null;
        }
        return weather;
    }

    private String buildTemperature(Float[] temperature){
        if (temperature == null)
            return "";

        Arrays.sort(temperature);
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(temperature).forEach(temp -> {
            stringBuilder.append(temp+",");
        });
        String temp = stringBuilder.toString();
        return temp.substring(0, temp.length()-1);
    }
}
