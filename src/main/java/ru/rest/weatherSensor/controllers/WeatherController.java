package ru.rest.weatherSensor.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.rest.weatherSensor.DTO.WeatherDTO;
import ru.rest.weatherSensor.DTO.WeatherToSendDTO;
import ru.rest.weatherSensor.services.WeatherService;
import ru.rest.weatherSensor.util.errors.ErrorResponse;
import ru.rest.weatherSensor.util.errors.WeatherAddError;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/new")
    public String addWeather(@RequestBody @Valid WeatherDTO weatherDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessage.append(error.getField())
                        .append((" - "))
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new WeatherAddError(errorMessage.toString());
        }
        if (!weatherService.sensorRegistrationCheck(weatherService.convertToWeather(weatherDTO))) {
            return "Unregistered sensor, measurement was not added";
        } else if (weatherService.sensorRegistrationCheck(weatherService.convertToWeather(weatherDTO))) {
            weatherService.addWeather(weatherService.convertToWeather(weatherDTO));
            return "Weather measurements added successfully";
        }
        return "Bad request";
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(WeatherAddError e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @GetMapping("/all")
//    public List <WeatherDTO> getAllMeasurements(){
//        return weatherService.weatherList().stream().map(this::convertToWeatherDTO)
//                .collect(Collectors.toList());
//    }

    @GetMapping("/all")
    public List<WeatherToSendDTO> getAllMeasurements() {
        return weatherService.convertWeatherToSendDTO(weatherService.weatherList());
    }

    @GetMapping("/raining")
    public String rainingDay() {
        return "The raining day quantity is " + weatherService.rainingDayCount();
    }


}
