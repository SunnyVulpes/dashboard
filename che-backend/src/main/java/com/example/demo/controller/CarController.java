package com.example.demo.controller;

import com.example.demo.base.HttpResponse;
import com.example.demo.dao.CarDao;
import com.example.demo.dto.CarDto;
import com.example.demo.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/car")
public class CarController {
    @Autowired
    CarDao carDao;
    Car car;
    @PostConstruct
    public void postConstruct(){
        if(!carDao.findById(1L).isPresent()){
        car=new Car();
        carDao.saveAndFlush(car);
        }
        else{
            car=carDao.findById(1L).get();
        }
    }
    @GetMapping("/upload")
    public HttpResponse<String> upload(Double speed){
        car.setSpeed(speed);
        car.setUpdateTime(LocalDateTime.now());
        carDao.saveAndFlush(car);
        return HttpResponse.success("当前车速："+car.getSpeed());
    }
    @GetMapping("/get")
    public HttpResponse<CarDto> get(){
        return HttpResponse.success(car.convertToDto());
    }
}
