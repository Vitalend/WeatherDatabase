package ru.rest.weatherSensor.util.errors;

public class SensorCreatedError extends RuntimeException{
    public SensorCreatedError (String msg){
        super(msg);
    }
}
