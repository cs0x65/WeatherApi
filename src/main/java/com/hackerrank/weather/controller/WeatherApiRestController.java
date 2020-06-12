package com.hackerrank.weather.controller;

import com.hackerrank.weather.dto.*;
import com.hackerrank.weather.dto.adapter.WeatherRequestDTOAdapter;
import com.hackerrank.weather.dto.adapter.WeatherResponseDTOAdapter;
import com.hackerrank.weather.dto.adapter.WeatherWithMinMaxTempResponseDTOAdapter;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.service.WeatherServiceImpl;
import com.hackerrank.weather.util.ControllerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WeatherApiRestController {
    Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private WeatherServiceImpl weatherServiceImpl;

    /**
     *
     * @param dto
     * @return
     *
     * API example:
     *
     * POST http://localhost:8000/weather : creates a new weather data point
     * {
     *   "date": "2020-06-08",
     *   "location": {
     *     "city": "Pandharpur",
     *     "state": "Maharashtra",
     *     "lat": 17.2,
     *     "lon": 74.8
     *   },
     *   "temperature": [
     *     38.8,
     *     35.1,
     *     24.2,
     *     35.3,
     *     26.0,
     *     32.1,
     *     30.9,
     *     33.0,
     *     24.2
     *   ]
     * }
     */
    @RequestMapping(path = "/weather", method = RequestMethod.POST)
    private ResponseEntity<? extends ResponseDTO> create(@RequestBody WeatherDTO dto){
        logger.info("Request = "+dto);
        if (dto.getId() != null && weatherServiceImpl.getById(dto.getId()) != null){
            return new ResponseEntity<>(new ErrorResponseDTO("The weather data with id:"+dto.getId()+
                    " already exists!"), HttpStatus.BAD_REQUEST);
        }
        WeatherRequestDTOAdapter adapter = new WeatherRequestDTOAdapter();
        Weather weather = adapter.build(dto);
        weather = weatherServiceImpl.create(weather);
        return new ResponseEntity<>(new WeatherResponseDTOAdapter().build(weather), HttpStatus.CREATED);
    }

    /**
     *
     * @param id
     * @return
     *
     * API example:
     *
     * GET http://localhost:8000/weather/14 : Will get a single weather data point identified by given id.
     */
    @RequestMapping(path = "/weather/{id}", method = RequestMethod.GET)
    private ResponseEntity<? extends ResponseDTO> getOne(@PathVariable Long id){
        if (weatherServiceImpl.getById(id) == null){
            return new ResponseEntity<>(new ErrorResponseDTO("The weather data with id: "+id+
                    " does not exist!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new WeatherResponseDTOAdapter().build(weatherServiceImpl.getById(id)),
                HttpStatus.OK);
    }

    /**
     *
     * @param queryParams
     * @return
     *
     * API examples:
     *
     * GET http://localhost:8000/weather : Will get all the weather data
     *
     * GET http://localhost:8000/weather?lat=13.78&lon=73.10 : Will get all the weather data @ particular location
     *
     * GET http://localhost:8000/weather?start=2020-06-06&endDate=2020-06-07 : Will get all the weather data
     * in the given date range
     *
     * GET http://localhost:8000/weather?startDate=2020-06-06&end=2020-06-08&lat=17.2&lon=74.8 : Will get all the
     * weather data with lowest & highest temperature values in the given date range  @ particular location.
     */
    @RequestMapping(path = "/weather", method = RequestMethod.GET)
    private ResponseEntity<?> getAll(@RequestParam Map<String, String> queryParams){
        /**
         * It's not really a great thing to use wildcard in the return type ResponseEntity<?>.
         * Probably that goes to show why in 1st place, there seems to have a separate API to publish the list of
         * locations with lowest & highest temperatures.
         *
         * But even then, for each controller we can have a possible error response to return - so again we can't keep
         * the bounded or specific parameterized type for the ResponseEntity being returned.
         *
         * Unlike Java, Python allows to provide union of types as a type hint for generics.
         *
         * TODO: think of alternative.
         */
        logger.info("queryParams = "+queryParams);
        if (!queryParams.isEmpty()){
            ControllerUtil.FILTER filter = ControllerUtil.getFilter(queryParams);
            if (filter == ControllerUtil.FILTER.DATE_RANGE_LOCATION_FILTER){
                return new ResponseEntity<>(new WeatherWithMinMaxTempResponseDTOAdapter().buildAll(
                        weatherServiceImpl.getByFilter(queryParams)), HttpStatus.OK);
            }

            List<Weather> list;
            if (filter == ControllerUtil.FILTER.DATE_RANGE_FILTER){
                String[] dates = ControllerUtil.getStartAndEndDates(queryParams);
                list = weatherServiceImpl.getAllBetween(dates[0], dates[1]);
            }else {
                String[] latLng = ControllerUtil.getLocationLatLng(queryParams);
                if (weatherServiceImpl.getLocation(latLng[0], latLng[1]) == null){
                    return new ResponseEntity<>(new ErrorResponseDTO("The location with lat,lng: ("+
                            latLng[0]+", "+latLng[1]+")"+" does not exist!"), HttpStatus.NOT_FOUND);
                }
                list = weatherServiceImpl.getAllByLocationLatLng(latLng[0], latLng[1]);
            }
            return new ResponseEntity<>(new WeatherResponseDTOAdapter().buildAll(list), HttpStatus.OK);
        }
        return new ResponseEntity<>(new WeatherResponseDTOAdapter().buildAll(weatherServiceImpl.getAll()),
                HttpStatus.OK);
    }

    /**
     *
     * @param start compulsory query param start date
     * @param end compulsory query param end date
     * @return list of weather data points with lowest and highest temperature on each date
     *
     * API example:
     *
     * GET http://localhost:8000/weather/temperature?start=2020-06-06&end=2020-06-08 : Will get all the
     * weather data with lowest & highest temperature values in the given date range.
     *
     * [
     *     {
     *         "city": "Pandharpur",
     *         "state": "Maharashtra",
     *         "lat": 17.2,
     *         "lon": 74.8,
     *         "date": "2020-06-07",
     *         "lowest": 20.9,
     *         "highest": 33.0
     *     },
     *     {
     *         "city": "Pandharpur",
     *         "state": "Maharashtra",
     *         "lat": 17.2,
     *         "lon": 74.8,
     *         "date": "2020-06-08",
     *         "lowest": 30.9,
     *         "highest": 36.0
     *     },
     *     {
     *         "city": "Pune",
     *         "state": "Maharashtra",
     *         "lat": 13.78,
     *         "lon": 73.1,
     *         "date": "2020-06-08",
     *         "lowest": 30.8,
     *         "highest": 36.0
     *     },
     *     {
     *         "city": "Pune",
     *         "state": "Maharashtra",
     *         "lat": 13.78,
     *         "lon": 73.1,
     *         "date": "2020-06-08",
     *         "lowest": 20.8,
     *         "highest": 35.3
     *     },
     *     {
     *         "city": "Pandharpur",
     *         "state": "Maharashtra",
     *         "lat": 17.2,
     *         "lon": 74.8,
     *         "date": "2020-06-08",
     *         "lowest": 20.9,
     *         "highest": 38.8
     *     }
     * ]
     */
    @RequestMapping(path = "/weather/temperature", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    private List<WeatherWithMinMaxTempResponseDTO> getAllTemperatures(@RequestParam String start,
                                                                      @RequestParam String end){
        logger.info("start = "+start);
        logger.info("end = "+end);
        return new WeatherWithMinMaxTempResponseDTOAdapter().buildAll(weatherServiceImpl.getAllBetween(start, end));
    }

    /**
     *
     * @param date compulsory query param
     * @param lat compulsory query param location latitude
     * @param lon compulsory query param location longitude
     * @return list of weather data points
     *
     * API example:
     *
     * GET http://localhost:8000/weather/locations?date=20200608&lat=13.78&lon=73.10 : Will get all the weather data
     * @ particular location on a given date.
     */
    @RequestMapping(path = "/weather/locations", method = RequestMethod.GET)
    private ResponseEntity<?> getAllByLocation(@RequestParam String date, @RequestParam String lat,
                                              @RequestParam String lon){
        logger.info("date = "+date);
        logger.info("lat = "+lat);
        logger.info("lon = "+lon);
        if (weatherServiceImpl.getLocation(lat, lon) == null){
            return new ResponseEntity<>(new ErrorResponseDTO("The location with lat,lng: ("+
                    lat+", "+lon+")"+" does not exist!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new WeatherResponseDTOAdapter().
                buildAll(weatherServiceImpl.getAllByLocationLatLngAndDate(lat, lon, date)),
                HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @return
     *
     * API example:
     *
     * DELETE http://localhost:8000/erase/26 : Will delete a single weather data point identified by given id.
     */
    @RequestMapping(path = "/erase/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<? extends ResponseDTO> deleteOne(@PathVariable Long id){
        if (weatherServiceImpl.getById(id) == null){
            return new ResponseEntity<>(new ErrorResponseDTO("The weather data with id: "+id+
                    " does not exist!"), HttpStatus.NOT_FOUND);
        }
        weatherServiceImpl.deleteById(id);
        return new ResponseEntity<>(new SuccessResponseDTO(""), HttpStatus.NO_CONTENT);
    }

    /**
     *
     * @param queryParams
     *
     * API examples:
     *
     * DELETE http://localhost:8000/erase : Will delete all the weather data.
     *
     * DELETE http://localhost:8000/erase?startDate=2020-06-06&end=2020-06-06 : Will delete all the weather data in the
     * given data range.
     *
     * DELETE http://localhost:8000/erase?lat=13.78&longitude=73.10 : Will delete all the weather data
     * @ particular location.
     *
     * DELETE http://localhost:8000/erase?start=2020-06-06&endDate=2020-06-08&latitude=13.78&lon=73.10 :
     * Will delete all the weather data in the given date range at a particular location
     */
    @RequestMapping(path = "/erase", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    private void deleteAll(@RequestParam Map<String, String> queryParams){
        logger.info("queryParams = "+queryParams);
        if (!queryParams.isEmpty()){
            ControllerUtil.FILTER filter = ControllerUtil.getFilter(queryParams);
            if (filter == ControllerUtil.FILTER.DATE_RANGE_LOCATION_FILTER){
                weatherServiceImpl.deleteByFilter(queryParams);
            }else if (filter == ControllerUtil.FILTER.DATE_RANGE_FILTER){
                String[] dates = ControllerUtil.getStartAndEndDates(queryParams);
                weatherServiceImpl.deleteAllBetween(dates[0], dates[1]);
            }else {
                String[] latLng = ControllerUtil.getLocationLatLng(queryParams);
                weatherServiceImpl.deleteAllByLocationLatLng(latLng[0], latLng[1]);
            }
        }else {
            weatherServiceImpl.deleteAll();
        }
    }
}