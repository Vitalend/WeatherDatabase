package ru.rest.weatherSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.rest.weatherSensor.DTO.SensorDTO;
import ru.rest.weatherSensor.models.Sensor;
import ru.rest.weatherSensor.services.SensorService;
import ru.rest.weatherSensor.util.errors.ErrorResponse;
import ru.rest.weatherSensor.util.errors.SensorCreatedError;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/new")
    public String newSensor(@RequestBody @Valid SensorDTO sensorDTO,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessage.append(error.getField())
                        .append((" - "))
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new SensorCreatedError(errorMessage.toString());
        }
        if (!sensorService.sensorNameCheck(convertToSensor(sensorDTO))) {
            return "The sensor is already exists (from controller)";
        } else if (sensorService.sensorNameCheck(convertToSensor(sensorDTO))) {
            sensorService.addSensor(convertToSensor(sensorDTO));
            return "The new sensor was added successfully";
        }
        return "Bad request, check fields quantity (controller)";
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorCreatedError e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {

        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
