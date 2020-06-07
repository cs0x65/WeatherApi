package com.hackerrank.weather.dto.adapter;

import com.hackerrank.weather.dto.LocationDTO;
import com.hackerrank.weather.model.Location;

public class LocationRequestAdapter implements RequestDTOAdapter<LocationDTO, Location>{
    @Override
    public Location build(LocationDTO dto) {
        // use reflection
        Location location = new Location();
        location.setId(dto.getId());
        location.setCityName(dto.getCityName());
        location.setStateName(dto.getStateName());
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        return location;
    }
}
