package com.hackerrank.weather.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "location")
public class Location extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loc_gen")
    @SequenceGenerator(name = "loc_gen", sequenceName = "location_seq", allocationSize = 1)
    private Long id;

    private String cityName;
    private String stateName;
    @Column(unique = true)
    private Float latitude;
    @Column(unique = true)
    private Float longitude;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Weather> weatherData;

    public Location() {
    }

    public Location(String cityName, String stateName, Float latitude, Float longitude) {
        this.cityName = cityName;
        this.stateName = stateName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public List<Weather> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(List<Weather> weatherData) {
        this.weatherData = weatherData;
    }
}
