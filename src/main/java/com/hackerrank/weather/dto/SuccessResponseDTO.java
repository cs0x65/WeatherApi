package com.hackerrank.weather.dto;

public class SuccessResponseDTO implements ResponseDTO{
    private String message;

    public SuccessResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
