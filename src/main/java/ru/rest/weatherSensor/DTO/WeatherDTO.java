package ru.rest.weatherSensor.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class WeatherDTO {


    @NotNull(message = "Should not be empty")
    @Min(value = -100, message = "The temperature is to low")
    @Max(value = 100, message = "The temperature is to hi")
    private Integer value;

    @NotNull(message = "Should not be empty")
    private Boolean raining;

    @NotEmpty(message = "Should not be empty")
    private String sensor;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
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
}
