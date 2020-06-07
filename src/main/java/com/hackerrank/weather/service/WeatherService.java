package com.hackerrank.weather.service;

import com.hackerrank.weather.model.Weather;

import java.util.List;
import java.util.Map;

public interface WeatherService {
    public Weather create(Weather weather);
    public List<Weather> getAll();
    public Weather getById(Long id);
    public List<Weather> getAllByLocationLatLng(String latitude, String longitude);
    public List<Weather> getAllBetween(String startDate, String endDate);
    public List<Weather> getByFilter(Map<String, String > filter);
    public void deleteAll();
    public void deleteById(Long id);
    public void deleteAllByLocationLatLng(String latitude, String longitude);
    public void deleteAllBetween(String startDate, String endDate);
}
