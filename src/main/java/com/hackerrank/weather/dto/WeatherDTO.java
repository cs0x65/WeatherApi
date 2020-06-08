package com.hackerrank.weather.dto;

public class WeatherDTO implements RequestDTO, ResponseDTO{
    private Long id;
    private String date;
    private LocationDTO location;
    private Float[] temperature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public Float[] getTemperature() {
        return temperature;
    }

    public void setTemperature(Float[] temperature) {
        this.temperature = temperature;
    }
}
