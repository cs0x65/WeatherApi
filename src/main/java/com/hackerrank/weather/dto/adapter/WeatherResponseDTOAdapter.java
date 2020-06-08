package com.hackerrank.weather.dto.adapter;

import com.hackerrank.weather.dto.WeatherDTO;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.util.DTOUtil;
import com.hackerrank.weather.util.DateTimeUtil;

import java.util.List;
import java.util.stream.Collectors;

public class WeatherResponseDTOAdapter implements ResponseDTOAdapter<Weather, WeatherDTO>{

    @Override
    public WeatherDTO build(Weather weather) {
        WeatherDTO dto = new WeatherDTO();
        dto.setId(weather.getId());
        dto.setLocation(new LocationResponseAdapter().build(weather.getLocation()));
        dto.setTemperature(DTOUtil.buildTemperature(weather.getTemperature()));
        dto.setDate(DateTimeUtil.getDateFormat().format(weather.getDateRecorded()));
        return dto;
    }

    @Override
    public List<WeatherDTO> buildAll(List<Weather> weathers) {
        return weathers.stream().
                map(weather -> build(weather)).
                collect(Collectors.toList());
    }
}
