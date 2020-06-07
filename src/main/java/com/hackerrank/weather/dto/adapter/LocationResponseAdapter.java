package com.hackerrank.weather.dto.adapter;

import com.hackerrank.weather.dto.LocationDTO;
import com.hackerrank.weather.model.Location;

import java.util.List;

public class LocationResponseAdapter implements ResponseDTOAdapter<Location, LocationDTO>{
    @Override
    public LocationDTO build(Location location) {
        // use reflection
        LocationDTO dto = new LocationDTO();
        dto.setId(location.getId());
        dto.setCityName(location.getCityName());
        dto.setStateName(location.getStateName());
        dto.setLatitude(location.getLatitude());
        dto.setLongitude(location.getLongitude());
        return dto;
    }

    @Override
    public List<LocationDTO> buildAll(List<Location> locations) {
        throw new UnsupportedOperationException();
    }
}
