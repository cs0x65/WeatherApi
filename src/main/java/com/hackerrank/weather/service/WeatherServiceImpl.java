package com.hackerrank.weather.service;

import com.hackerrank.weather.model.Location;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.LocationRepository;
import com.hackerrank.weather.repository.WeatherRepository;
import com.hackerrank.weather.util.ControllerUtil;
import com.hackerrank.weather.util.DateTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    Logger logger = LogManager.getLogger(getClass().getName());

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
        return weatherRepository.findById(id).orElse(null);
    }

    @Override
    public List<Weather> getAllByLocationLatLng(String latitude, String longitude) {
        return  locationRepository.findByLatitudeAndLongitude(Float.parseFloat(latitude), Float.parseFloat(longitude)).
                getWeatherData();
    }

    @Override
    public List<Weather> getAllByLocationLatLngAndDate(String latitude, String longitude, String date) {
        List<Weather> list = null;
        try {
            list = weatherRepository.findByDateRecordedAndLocationLatitudeAndLocationLongitude(
                    DateTimeUtil.getDateFormat().parse(date),
                    Float.parseFloat(latitude),
                    Float.parseFloat(longitude));
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("Error retrieving weather data at location lat,lng: ("+latitude+", "+longitude+") and date: "+
                    date);
            logger.error(e);
        }
        return list;
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
        String[] latLng = ControllerUtil.getLocationLatLng(filter);
        String[] dates = ControllerUtil.getStartAndEndDates(filter);

        List<Weather> list = null;
        try {
            list = weatherRepository.findByDateRecordedBetweenAndLocationLatitudeAndLocationLongitude(
                    DateTimeUtil.getDateFormat().parse(dates[0]),
                    DateTimeUtil.getDateFormat().parse(dates[1]),
                    Float.parseFloat(latLng[0]),
                    Float.parseFloat(latLng[1]));
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("Error retrieving weather data between date range: ("+dates[0]+", "+dates[1]+") and " +
                    "location:("+latLng[0]+", "+latLng[1]+")");
            logger.error(e);
        }
        return list;
    }

    @Override
    public Location getLocation(String latitude, String longitude) {
        return locationRepository.findByLatitudeAndLongitude(Float.parseFloat(latitude), Float.parseFloat(longitude));
    }

    @Override
    public void deleteAll() {
        logger.warn("Deleting all weather data!");
        weatherRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.warn("Deleting weather data by id: "+id);
        weatherRepository.deleteById(id);
    }

    @Override
    public void deleteAllByLocationLatLng(String latitude, String longitude) {
        logger.warn("Deleting weather data by lat, long: ("+latitude+", "+longitude+")");
        Long count = weatherRepository.deleteByLocationLatitudeAndLocationLongitude(Float.parseFloat(latitude),
                Float.parseFloat(longitude));
        logger.info("Num weather data deleted = "+count);
    }

    @Override
    public void deleteAllBetween(String startDate, String endDate) {
        logger.warn("Deleting weather data between start, end: ("+startDate+", "+endDate+")");
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

    @Override
    public void deleteByFilter(Map<String, String> filter) {
        String[] latLng = ControllerUtil.getLocationLatLng(filter);
        String[] dates = ControllerUtil.getStartAndEndDates(filter);
        logger.warn("Deleting weather data by lat, long: ("+latLng[0]+", "+latLng[1]+")"
                +" start, end: ("+dates[0]+", "+dates[1]+")");
        try {
            Long count = weatherRepository.deleteByDateRecordedBetweenAndLocationLatitudeAndLocationLongitude(
                    DateTimeUtil.getDateFormat().parse(dates[0]),
                    DateTimeUtil.getDateFormat().parse(dates[1]),
                    Float.parseFloat(latLng[0]),
                    Float.parseFloat(latLng[1])
            );
            logger.info("Num weather data deleted = "+count);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("Error deleting weather data between date range: ("+dates[0]+", "+dates[1]+") and " +
                    "location:("+latLng[0]+", "+latLng[1]+")");
            logger.error(e);
        }
    }
}
