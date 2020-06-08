package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    public List<Weather> findByDateRecordedBetween(Date startDate, Date endDate);

    public List<Weather> findByDateRecordedBetweenAndLocationLatitudeAndLocationLongitude(
            Date startDate, Date endDate, Float latitude, Float longitude);

    public List<Weather> findByDateRecordedAndLocationLatitudeAndLocationLongitude(Date date, Float latitude,
                                                                              Float longitude);

    @Transactional
    public Long deleteByDateRecordedBetween(Date startDate, Date endDate);

    @Transactional
    public Long deleteByLocationLatitudeAndLocationLongitude(Float latitude, Float longitude);

    @Transactional
    public Long deleteByDateRecordedBetweenAndLocationLatitudeAndLocationLongitude(
            Date startDate, Date endDate, Float latitude, Float longitude);
}
