package com.tpe.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.tpe.domain.Car;
import com.tpe.dto.AppLogDTO;
import com.tpe.dto.CarDTO;
import com.tpe.dto.CarRequest;
import com.tpe.enums.AppLogLevel;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    //Not: Save() **********************************************************
    public void saveCar(CarRequest carRequest) {

        Car car = modelMapper.map(carRequest, Car.class);
        carRepository.save(car);

        // !!! loglama islemleri

        // !!! Log Appp bulunuyor
        InstanceInfo instanceInfo = eurekaClient.getApplication("log-service")
                .getInstances().get(0);
        // !!! request olusturuluyor
        String baseUrl = instanceInfo.getHomePageUrl(); // !!! http://localhost:portNO

        String path ="/log";
        String servicePath = baseUrl + path ;

        AppLogDTO appLogDTO = new AppLogDTO();
        appLogDTO.setLevel(AppLogLevel.INFO.name());
        appLogDTO.setDescription("Save a Car: " + car.getId());
        appLogDTO.setTime(LocalDateTime.now());

        ResponseEntity<String> logResponse = restTemplate.postForEntity(servicePath, appLogDTO, String.class);
        if(!(logResponse.getStatusCode()== HttpStatus.CREATED)){
            throw new ResourceNotFoundException("Log is not created");
        }

    }

    //Not: getAllCars() *********************************************************************
    public List<CarDTO> getAllCars(){ // carDTO --> core application da olusturulacak ve  burada poma eklenecek
        List<Car> carList= carRepository.findAll();
        List<CarDTO> carDTOList= carList
                .stream()
                .map(this::mapCarToCarDTO)// !!! mapCarToCarDTO hemen asagida
                .collect(Collectors.toList());
        return carDTOList;
    }
    // !!! getAllCars() icin yardimci method
    private CarDTO mapCarToCarDTO(Car car) {
        CarDTO carDTO= modelMapper.map(car, CarDTO.class);
        return carDTO;
    }

    //Not: getById() *********************************************************************
    public CarDTO getById(Long id) {
        Car car=carRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Car not found with id:"+id));
        CarDTO carDTO=mapCarToCarDTO(car); // !!! getAllCar daki yardimci metod
        return carDTO;
    }
}
