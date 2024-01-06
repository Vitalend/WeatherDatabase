package ru.rest.weatherSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rest.weatherSensor.models.Weather;

public interface WeatherRepository extends JpaRepository <Weather, Integer> {
}
