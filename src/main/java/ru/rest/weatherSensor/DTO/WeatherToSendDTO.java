package ru.rest.weatherSensor.DTO;

import java.time.LocalDateTime;

public class WeatherToSendDTO {
    private int value;
    private Boolean raining;
    private String sensor;

    private LocalDateTime measurementTime;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(LocalDateTime measurementTime) {
        this.measurementTime = measurementTime;
    }

    public WeatherToSendDTO(int value, Boolean raining, String sensor, LocalDateTime measurementTime) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.measurementTime = measurementTime;
    }
    public WeatherToSendDTO (){}
}
