package ru.rest.weatherSensor.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @Size(min = 3, max = 30, message = "Wrong size, the name must be between 3 and 30 characters")
    @NotEmpty (message = "The name should not be empty")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
