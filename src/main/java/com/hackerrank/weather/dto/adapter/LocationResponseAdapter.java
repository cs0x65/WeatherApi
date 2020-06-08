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
        dto.setCity(location.getCityName());
        dto.setState(location.getStateName());
        dto.setLat(location.getLatitude());
        dto.setLon(location.getLongitude());
        return dto;
    }

    @Override
    public List<LocationDTO> buildAll(List<Location> locations) {
        throw new UnsupportedOperationException();
    }
}
