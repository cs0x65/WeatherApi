package com.hackerrank.weather.dto;

public class LocationDTO implements RequestDTO, ResponseDTO{
    private Long id;
    private String city;
    private String state;
    private Float lat;
    private Float lon;

    public LocationDTO(){
    }

    public LocationDTO(String city, String state, Float lat, Float lon) {
        this.city = city;
        this.state = state;
        this.lat = lat;
        this.lon = lon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
