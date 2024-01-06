package ru.rest.weatherSensor;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.rest.weatherSensor.DTO.WeatherToSendDTO;

@SpringBootApplication
public class WeatherSensorApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherSensorApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	@Bean
	public WeatherToSendDTO weatherToSendDTO(){
		return new WeatherToSendDTO();
	}

}