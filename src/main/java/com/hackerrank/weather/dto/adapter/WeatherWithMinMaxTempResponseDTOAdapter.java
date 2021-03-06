package com.hackerrank.weather.dto.adapter;

import com.hackerrank.weather.dto.WeatherWithMinMaxTempResponseDTO;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.util.DateTimeUtil;

import java.util.List;
import java.util.stream.Collectors;

public class WeatherWithMinMaxTempResponseDTOAdapter implements
        ResponseDTOAdapter<Weather, WeatherWithMinMaxTempResponseDTO>{

    @Override
    public WeatherWithMinMaxTempResponseDTO build(Weather weather) {
        WeatherWithMinMaxTempResponseDTO dto = new WeatherWithMinMaxTempResponseDTO();
        dto.setCity(weather.getLocation().getCityName());
        dto.setState(weather.getLocation().getStateName());
        dto.setLat(weather.getLocation().getLatitude());
        dto.setLon(weather.getLocation().getLongitude());
        dto.setDate(DateTimeUtil.getDateFormat().format(weather.getDateRecorded()));
        dto.setDate(DateTimeUtil.getDateFormat().format(weather.getDateRecorded()));
        dto.setHighest(weather.getHighestTemp());
        dto.setLowest(weather.getLowestTemp());
        return dto;
    }

    @Override
    public List<WeatherWithMinMaxTempResponseDTO> buildAll(List<Weather> weathers) {
        return weathers.stream().
                map(weather -> build(weather)).
                collect(Collectors.toList());
    }
}
