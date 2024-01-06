package ru.rest.weatherSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rest.weatherSensor.models.Sensor;
import ru.rest.weatherSensor.repositories.SensorRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;
    private final Environment environment;

    @Autowired
    public SensorService(SensorRepository sensorRepository, Environment environment) {
        this.sensorRepository = sensorRepository;
        this.environment = environment;
    }

    @Transactional
    public void addSensor(Sensor sensor) {

        fullSensorSave(sensor);

        sensorRepository.save(sensor);
    }

    private void fullSensorSave(Sensor sensor) {
        sensor.setCreatedTime(LocalDateTime.now());
    }


    public boolean sensorNameCheck(Sensor sensor) {
        boolean checkResult = true;

        try {
            Connection connection = DriverManager.getConnection
                    (Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                            environment.getProperty("spring.datasource.username"),
                            environment.getProperty("spring.datasource.password"));
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sensor ");

            while (resultSet.next()) {
                if (sensor.getName().equals(resultSet.getString("name"))) {
                    checkResult = false;
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkResult;
    }

}
