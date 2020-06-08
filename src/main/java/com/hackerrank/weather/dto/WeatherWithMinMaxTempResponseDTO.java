package com.hackerrank.weather.dto;

public class WeatherWithMinMaxTempResponseDTO implements ResponseDTO{
    private String city;
    private String state;
    private Float lat;
    private Float lon;
    private String date;
    private Float lowest;
    private Float highest;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getLowest() {
        return lowest;
    }

    public void setLowest(Float lowest) {
        this.lowest = lowest;
    }

    public Float getHighest() {
        return highest;
    }

    public void setHighest(Float highest) {
        this.highest = highest;
    }
}
