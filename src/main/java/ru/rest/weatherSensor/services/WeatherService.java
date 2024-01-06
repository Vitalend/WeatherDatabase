package ru.rest.weatherSensor.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rest.weatherSensor.DTO.WeatherDTO;
import ru.rest.weatherSensor.DTO.WeatherToSendDTO;
import ru.rest.weatherSensor.models.Weather;
import ru.rest.weatherSensor.repositories.WeatherRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final Environment environment;

    private final ModelMapper modelMapper;

    private Weather weather;

    private final WeatherToSendDTO weatherToSendDTO;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, Environment environment,
                          ModelMapper modelMapper, WeatherToSendDTO weatherToSendDTO) {
        this.weatherRepository = weatherRepository;
        this.environment = environment;
        this.modelMapper = modelMapper;
        this.weatherToSendDTO = weatherToSendDTO;
    }

    @Transactional
    public void addWeather(Weather weather) {

        fullWeatherSave(weather);

        weatherRepository.save(weather);
    }

    public List<Weather> weatherList() {
        return weatherRepository.findAll();
    }

    public String rainingDayCount() {
        int counter = 0;

        try {
            Connection connection = DriverManager.getConnection
                    (Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                            environment.getProperty("spring.datasource.username"),
                            environment.getProperty("spring.datasource.password"));
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery
                    ("SELECT raining FROM weather");

            while (resultSet.next()) {
                if (resultSet.getBoolean("raining")) {
                    counter++;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(counter);
    }

    private void fullWeatherSave(Weather weather) {
        weather.setMeasurementTime(LocalDateTime.now());
    }

    public boolean sensorRegistrationCheck(Weather weather) {
        boolean checkResult = false;

        try {
            Connection connection = DriverManager.getConnection
                    (Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                            environment.getProperty("spring.datasource.username"),
                            environment.getProperty("spring.datasource.password"));
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sensor ");

            while (resultSet.next()) {
                if (weather.getSensor().equals(resultSet.getString("name"))) {
                    checkResult = true;
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkResult;
    }

    public List<WeatherToSendDTO> convertWeatherToSendDTO(List<Weather> weathers) {
        List<WeatherToSendDTO> weatherToSendDTOList = new ArrayList<>();
        for (Weather weather1 : weathers) {
            weatherToSendDTOList.add(modelMapper.map(weather1, WeatherToSendDTO.class));
        }
        return weatherToSendDTOList;
    }

    public Weather convertToWeather(WeatherDTO weatherDTO) {

        return modelMapper.map(weatherDTO, Weather.class);
    }

    public WeatherDTO convertToWeatherDTO(Weather weather) {

        return modelMapper.map(weather, WeatherDTO.class);
    }
}
