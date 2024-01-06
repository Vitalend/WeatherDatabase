package ru.rest.weatherSensor.util.errors;

public class WeatherAddError extends RuntimeException{
    public WeatherAddError(String msg){
        super(msg);
    }
}
