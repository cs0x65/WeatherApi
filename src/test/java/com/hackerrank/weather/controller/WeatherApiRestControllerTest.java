package com.hackerrank.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.weather.dto.LocationDTO;
import com.hackerrank.weather.dto.WeatherDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherApiRestControllerTest {
    @Autowired
    MockMvc mvc;

    @AfterEach
    public void eraseAllTestEntities() throws Exception {
        // just deletes weather data, not locations, can autowire locationRepository to clean up
        // but SpringBootTest shall wrap tests in transactions & rollback on the conclusion of the test.
        mvc.perform(MockMvcRequestBuilders.delete("/erase"));
    }

    @Test
    public void testCreate() throws Exception {
        WeatherDTO weatherDTO = new WeatherDTO();
        weatherDTO.setDate("2020-06-19");
        weatherDTO.setTemperature(new Float[]{31.0f});
        LocationDTO locationDTO = new LocationDTO("Hyderabad", "Telangana", 14.80f, 78.20f);
        weatherDTO.setLocation(locationDTO);

        mvc.perform(MockMvcRequestBuilders.post("/weather")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(weatherDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/weather"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}