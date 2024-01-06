package ru.rest.weatherSensor.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    public Sensor(String name, LocalDateTime createdTime) {
        this.name = name;
        this.createdTime = createdTime;
    }

    public Sensor() {
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
