package com.example.demo.controller;

import com.example.demo.base.BackendException;
import com.example.demo.base.HttpResponse;
import com.example.demo.dao.TemperatureDao;
import com.example.demo.dto.TemperatureDto;
import com.example.demo.entity.Temperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/temperature")
public class TemperatureController {
    Double curr;
    @Autowired
    TemperatureDao temperatureDao;

    @PostConstruct
    public void init(){
        if(temperatureDao.findFirstByOrderByIdDesc().isPresent()){
            curr=temperatureDao.findFirstByOrderByIdDesc().get().getTemperature();
        }
    }
    @GetMapping("/put")
    public HttpResponse<String> put(Double temperature){
        curr=temperature;
        Temperature temperature1=new Temperature();
        temperature1.setTemperature(temperature);
        temperatureDao.saveAndFlush(temperature1);
        return HttpResponse.success("当前温度："+curr);
    }
    @GetMapping("/get")
    public HttpResponse<Double> get() {
        if (curr == null)throw new BackendException("还没用温度记录");
        return HttpResponse.success(curr);
    }
    @GetMapping("/getlist")
    public HttpResponse<List<TemperatureDto>> getlist(){
        return HttpResponse.success(temperatureDao.findAverageTemperaturelist(PageRequest.of(0, 20)));
    }
}
