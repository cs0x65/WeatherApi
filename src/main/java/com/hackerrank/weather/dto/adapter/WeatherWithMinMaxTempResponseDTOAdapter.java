package com.hackerrank.weather.dto.adapter;

import com.hackerrank.weather.dto.WeatherWithMinMaxTempResponseDTO;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.util.DTOUtil;
import com.hackerrank.weather.util.DateTimeUtil;

import java.util.List;
import java.util.stream.Collectors;

public class WeatherWithMinMaxTempResponseDTOAdapter implements
        ResponseDTOAdapter<Weather, WeatherWithMinMaxTempResponseDTO>{

    @Override
    public WeatherWithMinMaxTempResponseDTO build(Weather weather) {
        WeatherWithMinMaxTempResponseDTO dto = new WeatherWithMinMaxTempResponseDTO();
        dto.setCityName(weather.getLocation().getCityName());
        dto.setStateName(weather.getLocation().getStateName());
        dto.setLatitude(weather.getLocation().getLatitude());
        dto.setLongitude(weather.getLocation().getLongitude());
        dto.setDateRecorded(DateTimeUtil.getDateFormat().format(weather.getDateRecorded()));
        dto.setDateRecorded(DateTimeUtil.getDateFormat().format(weather.getDateRecorded()));

        Float[] temperature = DTOUtil.buildTemperature(weather.getTemperature());
        dto.setHighest(temperature[temperature.length-1]);
        dto.setLowest(temperature[0]);

        return dto;
    }

    @Override
    public List<WeatherWithMinMaxTempResponseDTO> buildAll(List<Weather> weathers) {
        return weathers.stream().
                map(weather -> build(weather)).
                collect(Collectors.toList());
    }
}
