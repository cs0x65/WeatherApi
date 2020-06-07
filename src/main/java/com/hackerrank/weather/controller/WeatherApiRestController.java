package com.hackerrank.weather.controller;

import com.hackerrank.weather.dto.ResponseDTO;
import com.hackerrank.weather.dto.WeatherDTO;
import com.hackerrank.weather.dto.adapter.WeatherRequestDTOAdapter;
import com.hackerrank.weather.dto.adapter.WeatherResponseDTOAdapter;
import com.hackerrank.weather.dto.adapter.WeatherWithMinMaxTempResponseDTOAdapter;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.service.WeatherServiceImpl;
import com.hackerrank.weather.util.ControllerUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WeatherApiRestController {
    Logger logger = Logger.getLogger(getClass());

    @Autowired
    private WeatherServiceImpl weatherServiceImpl;

    @RequestMapping(path = "/weather", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    private List<? extends ResponseDTO> getAll(@RequestParam Map<String, String> queryParams){
        logger.info("queryParams = "+queryParams);
        if (!queryParams.isEmpty()){
            ControllerUtil.FILTER filter = ControllerUtil.getFilter(queryParams);
            if (filter == ControllerUtil.FILTER.DATE_RANGE_LOCATION_FILTER){
                return new WeatherWithMinMaxTempResponseDTOAdapter().buildAll(
                        weatherServiceImpl.getByFilter(queryParams));
            }

            List<Weather> list;
            if (filter == ControllerUtil.FILTER.DATE_RANGE_FILTER){
                list = weatherServiceImpl.getAllBetween(queryParams.get("startDate"),
                        queryParams.get("endDate"));
            }else {
                list = weatherServiceImpl.getAllByLocationLatLng(queryParams.get("latitude"),
                        queryParams.get("longitude"));
            }
            return new WeatherResponseDTOAdapter().buildAll(list);
        }
        return new WeatherResponseDTOAdapter().buildAll(weatherServiceImpl.getAll());
    }

    @RequestMapping(path = "/weather/{id}", method = RequestMethod.GET)
    private WeatherDTO get(@PathVariable Long id){
        return new WeatherResponseDTOAdapter().build(weatherServiceImpl.getById(id));
    }

    @RequestMapping(path = "/weather", method = RequestMethod.POST)
    private ResponseEntity<WeatherDTO> create(@RequestBody WeatherDTO dto){
        if (dto.getId() != null && weatherServiceImpl.getById(dto.getId()) != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        WeatherRequestDTOAdapter adapter = new WeatherRequestDTOAdapter();
        Weather weather = adapter.build(dto);
        weather = weatherServiceImpl.create(weather);
        return new ResponseEntity<WeatherDTO>(new WeatherResponseDTOAdapter().build(weather), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/erase", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    private void deleteAll(@RequestParam Map<String, String> queryParams){
        logger.info("queryParams = "+queryParams);
        if (!queryParams.isEmpty()){
            ControllerUtil.FILTER filter = ControllerUtil.getFilter(queryParams);
            if (filter == ControllerUtil.FILTER.DATE_RANGE_FILTER){
                weatherServiceImpl.deleteAllBetween(queryParams.get("startDate"), queryParams.get("endDate"));
            }else {
                weatherServiceImpl.deleteAllByLocationLatLng(queryParams.get("latitude"), queryParams.get("longitude"));
            }
        }else {
            weatherServiceImpl.deleteAll();
        }
    }

    @RequestMapping(path = "/erase/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    private void delete(@PathVariable Long id){
        weatherServiceImpl.deleteById(id);
    }
}
