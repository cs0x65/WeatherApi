package com.hackerrank.weather.service;

import com.hackerrank.weather.util.DateTimeUtil;
import com.hackerrank.weather.model.Location;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.LocationRepository;
import com.hackerrank.weather.repository.WeatherRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private  LocationRepository locationRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    Logger logger = Logger.getLogger(getClass());

    @Override
    public Weather create(Weather weather) {
        Location location = locationRepository.findByCityNameAndStateNameAndLatitudeAndLongitude(
                weather.getLocation().getCityName(),
                weather.getLocation().getStateName(),
                weather.getLocation().getLatitude(),
                weather.getLocation().getLongitude());
        if (location != null){
            weather.setLocation(location);
        }
        return  weatherRepository.save(weather);
    }

    @Override
    public List<Weather> getAll() {
        return weatherRepository.findAll();
    }

    @Override
    public Weather getById(Long id) {
        return weatherRepository.findOne(id);
    }

    @Override
    public List<Weather> getAllByLocationLatLng(String latitude, String longitude) {
        return  locationRepository.findByLatitudeAndLongitude(Float.parseFloat(latitude), Float.parseFloat(longitude)).
                getWeatherData();
    }

    @Override
    public List<Weather> getAllBetween(String startDate, String endDate) {
        List<Weather> list = null;
        try {
            list = weatherRepository.findByDateRecordedBetween(DateTimeUtil.getDateFormat().parse(startDate),
                    DateTimeUtil.getDateFormat().parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("Error retrieving weather data between date range: ("+startDate+", "+endDate+")");
            logger.error(e);
        }
        return list;
    }

    @Override
    public List<Weather> getByFilter(Map<String, String> filter) {
        Float latitude = Float.parseFloat(filter.get("latitude"));
        Float longitude = Float.parseFloat(filter.get("longitude"));
        String startDate = filter.get("startDate");
        String endDate = filter.get("endDate");

        List<Weather> list = null;
        try {
            list = weatherRepository.findByDateRecordedBetweenAndLocationLatitudeAndLocationLongitude(
                    DateTimeUtil.getDateFormat().parse(startDate),
                    DateTimeUtil.getDateFormat().parse(endDate),
                    latitude,
                    longitude);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("Error retrieving weather data between date range: ("+startDate+", "+endDate+") and " +
                    "location:("+latitude+", "+longitude+")");
            logger.error(e);
        }
        return list;
    }

    @Override
    public void deleteAll() {
        weatherRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        weatherRepository.delete(id);
    }

    @Override
    public void deleteAllByLocationLatLng(String latitude, String longitude) {
        logger.warn("Deleting by lat, long: ("+latitude+", "+longitude+")");
        Long count = weatherRepository.deleteByLocationLatitudeAndLocationLongitude(Float.parseFloat(latitude),
                Float.parseFloat(longitude));
        logger.info("Num weather data deleted = "+count);
    }

    @Override
    public void deleteAllBetween(String startDate, String endDate) {
        logger.warn("Deleting by lat, long: ("+startDate+", "+endDate+")");
        try {
            Long count = weatherRepository.deleteByDateRecordedBetween(DateTimeUtil.getDateFormat().parse(startDate),
                    DateTimeUtil.getDateFormat().parse(endDate));
            logger.info("Num weather data deleted = "+count);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("Error deleting weather data between date range: ("+startDate+", "+endDate+")");
            logger.error(e);
        }
    }
}
