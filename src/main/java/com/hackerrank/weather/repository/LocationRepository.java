package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    public Location findByCityNameAndStateNameAndLatitudeAndLongitude(String cityName, String stateName,
                                                                     Float latitude, Float longitude);

    public Location findByLatitudeAndLongitude(Float latitude, Float longitude);
}
