package com.hackerrank.weather.model;

import com.hackerrank.weather.util.DataUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "weather")
public class Weather extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wid_gen")
    @SequenceGenerator(name = "wid_gen", sequenceName = "weather_seq", allocationSize = 1)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateRecorded;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    private String temperature;

    // this kind of change actually mandates the migration, which will set values for the existing records.
    @Column(name = "lowest_temp")
    private Float lowestTemp;

    @Column(name = "highest_temp")
    private Float highestTemp;

    public Weather() {
    }

    public Weather(Long id, Date dateRecorded, Location location, String temperature) {
        this.id = id;
        this.dateRecorded = dateRecorded;
        this.location = location;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Float getLowestTemp() {
        if (lowestTemp == null){
            lowestTemp = DataUtil.buildTemperature(temperature)[0];
        }
        return lowestTemp;
    }

    public void setLowestTemp(Float lowestTemp) {
        this.lowestTemp = lowestTemp;
    }

    public Float getHighestTemp() {
        if (highestTemp == null){
            Float[] temp = DataUtil.buildTemperature(temperature);
            highestTemp = temp[temp.length-1];
        }
        return highestTemp;
    }

    public void setHighestTemp(Float highestTemp) {
        this.highestTemp = highestTemp;
    }
}
