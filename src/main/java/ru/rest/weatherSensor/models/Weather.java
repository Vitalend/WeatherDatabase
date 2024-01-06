package ru.rest.weatherSensor.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
public class Weather {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    private int value;

    @Column(name = "raining")
    private Boolean raining;

    @Column(name = "sensor")
    private String sensor;

    @Column(name = "measurement_time")
    private LocalDateTime measurementTime;

    public Weather(int value, Boolean raining, String sensor,LocalDateTime measurementTime) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.measurementTime = measurementTime;
    }

    public Weather(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}